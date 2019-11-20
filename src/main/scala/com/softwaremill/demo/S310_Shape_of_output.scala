package com.softwaremill.demo

import sttp.tapir._
import sttp.tapir.json.circe._
import io.circe.generic.auto._
import sttp.model.{HeaderNames, StatusCode}

class S310_Shape_of_output {
  // What's the shape of an output?

  // Top-level coproduct: Either[E, O]
  case class Endpoint[I, E, O, +S]()

  // Basic values & products
  val o1: EndpointOutput[String] = header[String](HeaderNames.SetCookie)
  val o2: EndpointOutput[(String, String)] = header[String](HeaderNames.SetCookie).and(stringBody)

  // Additionally: coproduct based on status code
  sealed trait ErrorInfo
  case class NotFound(what: String) extends ErrorInfo
  case class Unauthorized(realm: String) extends ErrorInfo
  case class Unknown(code: Int, msg: String) extends ErrorInfo
  case object NoContent extends ErrorInfo

  val o3: EndpointOutput[ErrorInfo] = oneOf(
    statusMapping(StatusCode.NotFound, jsonBody[NotFound].description("not found")),
    statusMapping(StatusCode.Unauthorized, jsonBody[Unauthorized].description("unauthorized")),
    statusMapping(StatusCode.NoContent, emptyOutput.map(_ => NoContent)(_ => ())),
    statusDefaultMapping(jsonBody[Unknown].description("unknown"))
  )

  /*
  How to interpret?

  Client:
    the status code serves as a discriminator, we know which branch to use

  Server:
    ClassTag + isInstance (unfortunately)
    no exhaustiveness
 */
}
