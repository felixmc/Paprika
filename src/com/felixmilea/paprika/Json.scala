package com.felixmilea.paprika

import scala.language.dynamics
import com.felixmilea.paprika.parsing.JsonParser
import scala.io.Source

object Json {
  def apply(s: String) = JsonParser.parse(s)
  def apply(l: List[Any]) = new Json(l)
  def apply(m: Map[String, Any]) = new Json(m)
  
  def fromFile(path: String) = this(Source.fromFile(path).mkString)
  def fromUrl(url: String) = this(Source.fromURL(url, "utf-8").mkString)
  
  implicit def JsonToString(j: Json) = j.toString
  implicit def JsonToInt(j: Json) = j.toInt
  implicit def JsonToDouble(j: Json) = j.toDouble
  implicit def JsonToBool(j: Json) = j.toBool

  def makeJSON(a: Any): String = a match {
    case m: Map[Any, Any] => m.map {
      case (name, content) => makeJSON(name.toString) + ":" + makeJSON(content)
    }.mkString("{", ",", "}")
    case l: List[Any] => l.map(makeJSON).mkString("[", ",", "]")
    case s: String => "\"" + s + "\""
    case i: Int => i.toString
    case d: Double => d.toString
    case b: Boolean => b.toString
    case j: Json => makeJSON(j.o)
    case null => "null"
  }
}

class Json(val a: Any) extends Seq[Json] with Dynamic {
  
  val o = a match {
    case op: Option[Any] => op match {
      case Some(jt) => jt
      case None => null
    }
    case _ => a
  }
  
  override def toString: String = if (o == null) "null" else o.toString

  def toInt: Int = o match {
    case i: Integer => i
    case d: Double => d.toInt
    case _ => throw new JsonConversionException(o, "Int")
  }

  def toDouble: Double = o match {
    case d: Double => d
    case f: Float => f.toDouble
    case i: Integer => i.toDouble
    case _ => throw new JsonConversionException(o, "Double")
  }

  def toBool: Boolean = o match {
    case b: Boolean => b
    case "true" => true
    case "false" => false
    case _ => throw new JsonConversionException(o, "Boolean")
  }

  def apply(key: String): Json = o match {
    case m: Map[Any, Any] => new Json(m.get(key))
    case _ => throw new JsonElementNotFoundException(key)
  }

  def has(key: String): Boolean = o match {
    case m: Map[Any, Any] => m.contains(key)
    case _ => false
  }

  def apply(idx: Int): Json = o match {
    case a: List[Any] => new Json(a(idx))
    case _ => throw new JsonObjectNotArrayException
  }

  def length: Int = o match {
    case a: List[Any] => a.length
    case m: Map[Any, Any] => m.size
    case _ => throw new JsonElementNotFoundException("length")
  }

  def iterator: Iterator[Json] = o match {
    case a: List[Any] => new JsonIterator(a.iterator)
    case _ => throw new JsonObjectNotArrayException
  }

  def selectDynamic(name: String): Json = apply(name)

  def applyDynamic(name: String)(arg: Any) = {
    arg match {
      case s: String => apply(name)(s)
      case n: Int => apply(name)(n)
//      case u: Unit => apply(name)
    }
  }
}