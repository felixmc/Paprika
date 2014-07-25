package com.felixmilea.paprika

class JsonConversionException(value: Any, clazz: String)
extends Exception(JsonConversionException.compose(value,clazz))

object JsonConversionException {
  def compose(value: Any, clazz: String) : String = s"cannot convert value $value to type $clazz"
}

class JsonNotIterableException(json: Json) extends Exception("This JSON object is not iterable: " + json.o)