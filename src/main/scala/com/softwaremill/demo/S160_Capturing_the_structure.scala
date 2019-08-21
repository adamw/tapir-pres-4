package com.softwaremill.demo

import tapir.Codec.PlainCodec
import tapir.typelevel.ParamConcat
import tapir.{EndpointIO, EndpointInfo, EndpointInput, EndpointOutput, MediaType}

class S160_Capturing_the_structure {

  // Our programming languages are really good at manipulating data
  // Let's represent the description as data!

  case class Endpoint[I, E, O](input: EndpointInput[I], errorOutput: EndpointOutput[E], output: EndpointOutput[O], info: EndpointInfo) {

    // The data is created & refined using methods:
    def get: Endpoint[I, E, O] = ???
    def in[J, IJ](i: EndpointInput[J])(implicit ts: ParamConcat.Aux[I, J, IJ]): Endpoint[IJ, E, O] = ???
    // ...
  }

  def path[T: PlainCodec]: EndpointInput.PathCapture[T] = ???
  def stringBody: EndpointIO.Body[String, MediaType.TextPlain, String] = ???
  // ...

  // These methods give us nice syntax: that's the API
}
