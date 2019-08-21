package com.softwaremill.demo

import cats.effect.IO
import com.softwaremill.sttp.HeaderNames
import tapir._

class S330_Tuples_vs_functions {
  val e1: Endpoint[Int, Unit, String, Nothing] = endpoint
    .in(query[Int]("amount"))
    .out(stringBody)

  e1.serverLogic[IO] { amount =>
    IO(Right(s"Got: $amount"))
  }

  //

  val e2: Endpoint[(Int, String), Unit, String, Nothing] = endpoint
    .in(query[Int]("amount"))
    .in(header[String](HeaderNames.Authorization))
    .out(stringBody)

  /**
    * Server function: `(String, Int) => Future[Either[Unit, String]]`
    * Is it a 1-arg function taking a 2-tuple, or a 2-arg function?
    */
  e2.serverLogic[IO] {
    case (amount, body) => IO(Right(s"Got: $amount $body"))
  }

  // Additional typelevel computation (.serverLogic signature readability hit)
  // vs
  // syntactic difference when defining server logic
}
