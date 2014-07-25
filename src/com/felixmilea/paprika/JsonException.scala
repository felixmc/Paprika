package com.felixmilea.paprika

class JsonElementNotFoundException(key: String)
extends Exception(JsonElementNotFoundException.compose(key))

object JsonElementNotFoundException {
  
  def compose(key: String) : String = s"$key does not exist"
  
}

class JsonConversionException(value: Any, clazz: String)
extends Exception(JsonConversionException.compose(value,clazz))

object JsonConversionException {
  
  def compose(value: Any, clazz: String) : String = s"cannot convert value $value to type $clazz"
  
}

class JsonObjectNotArrayException extends Exception("The JSON object is not an array but is being used like one")