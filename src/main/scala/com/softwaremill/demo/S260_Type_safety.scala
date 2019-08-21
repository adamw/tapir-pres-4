package com.softwaremill.demo

import java.util.UUID

import cats.effect.IO
import tapir._

class S260_Type_safety {
  // Type-level computations to create input/output parameter lists

  val e1: Endpoint[Int, Unit, Unit, Nothing] = endpoint.in(query[Int]("amount"))
  val e2: Endpoint[(Int, String), Unit, Unit, Nothing] = e1.in(stringBody)
  val e3: Endpoint[(Int, String, UUID), Unit, Unit, Nothing] = e2.in("order" / path[UUID]("id"))

  e3.serverLogic[IO] { case (amount, body, id) =>
    IO(Right(()))
  }
}
