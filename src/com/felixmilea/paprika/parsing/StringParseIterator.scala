package com.felixmilea.paprika.parsing

import com.felixmilea.paprika.JsonParseException

class StringParseIterator(str: String) {
  private var i = 0;
  val data = str.trim()
  val length = data.length()
  
  def apply(in: Int) = if (in >= 0 && in < length) data(in) else null
  def index() = i
  
  def hasNext() : Boolean = i < length
  
  def next() : Char = {
    if (hasNext()) {
      val c = data(i)
      i = i + 1
      return c
    } else {
      throw new JsonParseException(this)
    }
  }
  
  def peek() : Char = {
    if (hasNext()) data(i)
    else throw new JsonParseException(this)
  }
  
  def nextNonWs() : Char = {
    var c = '\0'
    
    do {
      c = next
    } while (c.isWhitespace)
    
    return c
  }
  
  def peekNonWs() : Char = {
    var c = '\0'
    var ip = i
      
    do {
      c = data(ip)
      ip = ip + 1
    } while (c.isWhitespace)
    
    return c
  }
  
  def grabUntil(c: Char) : String = {
    val sb = new StringBuilder
    
    while (peek != c)
      sb += next
        
    return sb.toString()
  }
  
  def grabUntil(condition: Function[Char,Boolean]) : String = {
    val sb = new StringBuilder
    
    while (condition(peek))
      sb += next
        
    return sb.toString()
  }
  
  def grabUntilWs() : String = {
    val sb = new StringBuilder
    
    while (!peek.isWhitespace)
      sb += next
        
    return sb.toString()
  }

  def follow(str: String) {
    if (follows(str)) i += str.length
    else throw new JsonParseException(this)
  }
  
  def follows(str: String) : Boolean = {
    var io = i
    
    for (is <- 0 until str.length) {
      if (data(io) != str(is)) return false
      io = io + 1
    }
    
    return true
  }
  
  def followNonWs(str: String) {
      var ip = i
      
      while (data(ip).isWhitespace) {
        ip = ip + 1
      } 
      
      for (is <- 0 until str.length) {
        if (data(ip) != str(is)) return throw new JsonParseException(this)
        ip = ip + 1
      }
    
      i = ip
  }
  
  def followsNonWs(str: String) : Boolean = {
    var ip = i
      
    do {
      ip = ip + 1
    } while (data(ip).isWhitespace)
      
    for (is <- 0 until str.length) {
      if (data(ip) != str(is)) return false
      ip = ip + 1
    }
    
    return true
  }
  
}

object StringParseIterator {
  def apply(str: String) = new StringParseIterator(str)
}
