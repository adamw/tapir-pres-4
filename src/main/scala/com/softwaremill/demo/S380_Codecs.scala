package com.softwaremill.demo

import sttp.tapir._

class S380_Codecs {
  // These are different codecs:
  def c1: Codec[String, CodecFormat.TextPlain, String] = ???
  def c2: Codec[String, CodecFormat.Json, String] = ???

  // A query parameter always needs a text/plain codec:
  def query[T: CodecForMany[*, CodecFormat.TextPlain, String]](name: String): EndpointInput.Query[T] = ???

  // Body might need a json or text codec:
  def stringBody: EndpointIO.Body[String, CodecFormat.TextPlain, String] = ???
  def jsonBody[T](implicit codec: CodecForOptional[T, CodecFormat.Json, _]): EndpointIO.Body[T, CodecFormat.Json, _] = ???

  // Codecs are required when describing the endpoint, as this information is lost in the signature
}
