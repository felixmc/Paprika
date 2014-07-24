package com.felixmilea.paprika.parsing

trait ParseState {
  def evaluate(c: Char) : ParseState
}

object Error extends ParseState {
  def evaluate(c: Char) : ParseState = Error
}
  
object End extends ParseState {
  def evaluate(c: Char) : ParseState = End
}
  