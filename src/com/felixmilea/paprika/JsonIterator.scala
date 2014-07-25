package com.felixmilea.paprika

class JsonIterator(i: Iterator[Any]) extends Iterator[Json] {
  def hasNext = i.hasNext
  def next() = new Json(i.next())
}