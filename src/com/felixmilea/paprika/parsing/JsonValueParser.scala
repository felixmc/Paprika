package com.felixmilea.paprika.parsing

import com.felixmilea.paprika.parsing.JsonParseException

object JsonValueParser extends JParser {

  def parse(it: StringParseIterator) : Any = {
    
    return it.peekNonWs match {
      case OBJ_OPEN => JsonObjectParser.parse(it)
      case ARR_OPEN => JsonArrayParser.parse(it)
      case QUOTE => JsonStringParser.parse(it)
      case c if c.isDigit || c == '-' => JsonNumberParser.parse(it)
      case ('t' | 'f') => JsonBoolParser.parse(it)
      case 'n' => (it.followNonWs("null")); null
      case _ => throw new JsonParseException(it)
    }
  }
  
}