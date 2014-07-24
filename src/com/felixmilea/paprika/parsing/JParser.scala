package com.felixmilea.paprika.parsing

trait JParser {

  def parse(it: StringParseIterator) : Any
  
  val OBJ_OPEN  = '{'
  val OBJ_CLOSE = '}'
  val ARR_OPEN  = '['
  val ARR_CLOSE = ']'
  val QUOTE     = '"'
  val COMMA     = ','
  val COLON     = ':'
  val PLUS      = '+'
  val MINUS     = '-'
  val EXP_L     = 'e'
  val EXP_U     = 'E'
    
}