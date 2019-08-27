package com.softwaremill.demo

import tapir._

class S380_Codecs {
  // These are different codecs:
  def c1: Codec[String, MediaType.TextPlain, String] = ???
  def c2: Codec[String, MediaType.Json, String] = ???

  // A query parameter always needs a text/plain codec:
  def query[T: CodecForMany[?, MediaType.TextPlain, String]](name: String): EndpointInput.Query[T] = ???

  // Body might need a json or text codec:
  def stringBody: EndpointIO.Body[String, MediaType.TextPlain, String] = ???
  def jsonBody[T](implicit codec: CodecForOptional[T, MediaType.Json, _]): EndpointIO.Body[T, MediaType.Json, _] = ???

  // Codecs are required when describing the endpoint, as this information is lost in the signature
}
