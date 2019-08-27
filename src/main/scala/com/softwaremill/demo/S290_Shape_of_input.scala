package com.softwaremill.demo

import com.softwaremill.sttp.HeaderNames
import tapir._

class S290_Shape_of_input {
  // What's the shape of an input?

  // Basic value: header, body, query parameter, path parameter
  val i1: EndpointInput[Int] = query[Int]("amount")

  // A product: two or more inputs
  val i2: EndpointInput[(Int, String)] = query[Int]("amount").and(header[String](HeaderNames.Authorization))
}
