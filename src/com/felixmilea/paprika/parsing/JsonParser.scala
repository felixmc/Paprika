package com.felixmilea.paprika.parsing

import com.felixmilea.paprika.Json
import com.felixmilea.paprika.parsing.JsonParseException

object JsonParser extends JParser {
 
   def parse(str: String) : Json = {
     val it = new StringParseIterator(str)
     return parse(it)
   }
     
  def parse(it : StringParseIterator) : Json = {    
    return it.peekNonWs match {
      case OBJ_OPEN => Json(JsonObjectParser.parse(it))
      case ARR_OPEN => Json(JsonArrayParser.parse(it))
      case _ => throw new JsonParseException(it)
    }
    
  }
  
}