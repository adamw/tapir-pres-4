package com.softwaremill.demo

import tapir.EndpointInput

class S300_Shape_of_input {
  // What about coproducts?

  val alternateInput1: EndpointInput[Either[String, Int]] = ???

  sealed trait Fruit
  case class Apple(kind: String) extends Fruit
  case class Orange(size: Double) extends Fruit
  val alternateInput2: EndpointInput[Fruit] = ???

  // When interpreting as a client: how to match a value to a specific input?

  // Complicates the API
  // Little practical usage
  // Can be represented as a tuple of options + validation
}
