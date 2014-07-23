package com.felixmilea.paprika.parsing

import com.felixmilea.paprika.JsonParseException

class ParseStringIterator(str: String) {
  private var i = 0;
  val data = str.trim()
  val length = data.length()
  
  def apply(in: Int) = if (in >= 0 && in < length) data(in) else null
  def current() = data(i)
  def index() = i
  
  def hasNext() : Boolean = i + 1 < length
  
  def next() : Char = {
    if (hasNext()) {
      data(i)
    } else {
      throw new JsonParseException(data, i)
    }
  }
  
  def nextNonWs() : Char = {
    var c = '\0'
    
    do {
      c = next()
    } while (c.isWhitespace)
    
    
    return c
  }
  
}
