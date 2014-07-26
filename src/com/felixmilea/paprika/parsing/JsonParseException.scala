package com.felixmilea.paprika.parsing

class JsonParseException(data: String, pos: Int)
extends Exception(JsonParseException.compose(data, pos)) {
  def this(it: StringParseIterator) = this(it.data, it.index)
}

object JsonParseException {
  val peek = 10
  
  def compose(data: String, pos: Int) : String = {
    val sb = new StringBuilder
    
    sb ++= "A JSON parsing error occurred:\n"
    
    val after = Math.min(data.length(),pos + peek + 1)
    val line2 = s"  char($pos): " + data.substring(Math.max(0,pos - peek), after).replace("\n", " ")

    sb ++= line2 + "\n"
    sb ++= (" " * (line2.length() - (after - pos)))
    sb ++= "^"
    
    return sb.toString()
  }
  
}