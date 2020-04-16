package com.softwaremill.demo

import cats.effect.IO
import org.http4s.HttpRoutes
import org.http4s.dsl.io._
import org.http4s.circe.CirceEntityEncoder._
import io.circe.generic.auto._

object S120_Current_state_http4s {
  case class Book(title: String)

  object IdParamMatcher extends QueryParamDecoderMatcher[String]("id")
  val serverRoutes: HttpRoutes[IO] = HttpRoutes.of[IO] {
    case GET -> Root / "books" :? IdParamMatcher(id) => Ok(Book("The Trial"))
  }
}
