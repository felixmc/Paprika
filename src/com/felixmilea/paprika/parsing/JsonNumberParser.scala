package com.felixmilea.paprika.parsing

import com.felixmilea.paprika.JsonParseException

object JsonNumberParser extends JParser {
  
  def parse(it: StringParseIterator) : Double = {
    val sb = new StringBuilder()
    var state : ParseState = begin
    var c = it.peekNonWs
    
    do {
      state = state.evaluate(c)
      
      if (state == Error) throw new JsonParseException(it)    
      if (state != End) {
        sb += (if(sb.length == 0) it.nextNonWs else it.next)
        c = it.peek
      }
      
    } while (state != End)
    
    return sb.toString.toDouble
  }
    
  object begin extends ParseState {
    def evaluate(c: Char) : ParseState = c match {
      case '-' => firstDigit
      case c: Char => firstDigit.evaluate(c)
    }
  }
  
  object firstDigit extends ParseState {
    def evaluate(c: Char) : ParseState = c match {
      case '0' => preDecimal
      case c if c.isDigit => leftDigits
      case _ => Error
    }
  }

  object leftDigits extends ParseState {
    def evaluate(c: Char) : ParseState = 
      if (c.isDigit) leftDigits
      else preDecimal.evaluate(c)
  }
  
  object preDecimal extends ParseState {
    def evaluate(c: Char) : ParseState =
      if (c == '.') decimalPoint
      else postDecimal.evaluate(c)
  }
  
  object decimalPoint extends ParseState {
    def evaluate(c: Char) : ParseState =
      if (c.isDigit) decimal
      else Error
  }
  
  object decimal extends ParseState {
    def evaluate(c: Char) : ParseState = c match {
      case c if c.isDigit => decimal
      case _ => postDecimal.evaluate(c)
    }
  }
  
  object postDecimal extends ParseState {
    def evaluate(c: Char) : ParseState = c match {
      case (EXP_L | EXP_U) => exponent
      case _ => End
    }
  }

  object exponent extends ParseState {
    def evaluate(c: Char) : ParseState = c match {
      case ('+' | '-') => firstExponentDigit
      case c if c.isDigit => exponentDigit
      case _ => Error
    }
  }
  
  object firstExponentDigit extends ParseState {
    def evaluate(c: Char) : ParseState = c match {
      case c if c.isDigit => exponentDigit
      case _ => Error
    }
  }
  
  object exponentDigit extends ParseState {
    def evaluate(c: Char) : ParseState = c match {
      case c if c.isDigit => exponentDigit
      case _ => End
    }
  }

}