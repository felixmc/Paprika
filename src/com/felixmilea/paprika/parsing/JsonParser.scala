package com.felixmilea.paprika.parsing

import com.felixmilea.paprika.Json
import com.felixmilea.paprika.JsonParseException

object JsonParser extends JParser {
 
   def parse(str: String) : Json = {
     val it = new StringParseIterator(str)
     return parse(it)
   }
     
  def parse(it : StringParseIterator) : Json = {    
    val data = it.peekNonWs match {
      case OBJ_OPEN => JsonObjectParser.parse(it)
      case ARR_OPEN => JsonArrayParser.parse(it)
      case _ => throw new JsonParseException(it)
    }
    
    return Json(data)
  }
  
}