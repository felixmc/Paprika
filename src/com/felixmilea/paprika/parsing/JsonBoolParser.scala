package com.felixmilea.paprika.parsing

import com.felixmilea.paprika.JsonParseException

object JsonBoolParser extends JParser {
  
  def parse(it: StringParseIterator) : Boolean = {
    
    it.peekNonWs match {
      case 't' => it.followNonWs("true"); return true
      case 'f' => it.followNonWs("false"); return false
      case _ => throw new JsonParseException(it)
    }
      
  }
  
}