package com.felixmilea.paprika.parsing

import com.felixmilea.paprika.parsing.JsonParseException

object JsonStringParser extends JParser {

  def parse(it: StringParseIterator) : String = {
    if (it.nextNonWs != QUOTE) throw new JsonParseException(it)
    
    val sb = new StringBuilder()
    var escape = false
	if (it.peek != QUOTE) {
	  var value = '\0'
      do {
	    value = it.next
	    sb += value
	    escape = if(escape) false else if(value == '\\') true else false
	  } while (it.peekNonWs != QUOTE || (it.peekNonWs == QUOTE && escape))
    }
	
    if (it.nextNonWs != QUOTE) throw new JsonParseException(it)
    
    return sb.toString
  }
  
}