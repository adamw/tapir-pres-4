package com.softwaremill.demo

import cats.effect.IO
import sttp.tapir._

class S320_Limits_of_type_safety {
  // Which properties to verify at compile-time?
  // all?

  // types of inputs/outputs
  endpoint.in(query[Int]("amount")).out(stringBody).serverLogic[IO] { amount =>
    IO(Right(s"Got: $amount"))
  }

  // NOT: double body
  endpoint.in(stringBody).in(binaryBody[Array[Byte]])

  // NOT: some multipart restrictions
}
