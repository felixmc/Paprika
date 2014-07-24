package com.felixmilea.paprika.parsing

import scala.collection.mutable.ListBuffer
import com.felixmilea.paprika.JsonParseException

object JsonArrayParser extends JParser {
  
  def parse(it: StringParseIterator) : List[Any] = {    
	if (it.nextNonWs != ARR_OPEN) throw new JsonParseException(it)
    
    val list = new ListBuffer[Any]
    
	if (it.peekNonWs != ARR_CLOSE) {
      do {
        if (list.length != 0) it.nextNonWs()
	    val value = JsonValueParser.parse(it)      
	    list += value
	  } while (it.peekNonWs == COMMA)
    }
	
    if (it.nextNonWs != ARR_CLOSE) throw new JsonParseException(it)
	
    return list.result
  }
  
}