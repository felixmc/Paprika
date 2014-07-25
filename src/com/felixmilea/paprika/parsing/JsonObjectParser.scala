package com.felixmilea.paprika.parsing

import com.felixmilea.paprika.parsing.JsonParseException
import scala.collection.mutable.MapBuilder

object JsonObjectParser extends JParser {

  def parse(it: StringParseIterator) : Map[String, Any] = {
    if (it.nextNonWs != OBJ_OPEN) throw new JsonParseException(it)
    val obj = new MapBuilder[String, Any,Map[String, Any]](Map[String, Any]())
    
    if (it.peekNonWs != OBJ_CLOSE) {
      do {
        if (obj.result.size != 0) it.nextNonWs()
	    val key = JsonStringParser.parse(it)
	    if (it.nextNonWs != COLON) throw new JsonParseException(it)
	
	    val value = JsonValueParser.parse(it)
	      
	    obj += (key -> value)
	      
      } while (it.peekNonWs == COMMA)
    }
    
    if (it.nextNonWs != OBJ_CLOSE) throw new JsonParseException(it)
    
    return obj.result
  }
  
}