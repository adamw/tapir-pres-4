package com.softwaremill.demo

import cats.effect.IO
import org.http4s.HttpRoutes
import org.http4s.dsl.io._
import com.softwaremill.sttp._

class S120_General_problem {

  // server
  object NameParamMatcher extends QueryParamDecoderMatcher[String]("name")
  val serverRoutes: HttpRoutes[IO] = HttpRoutes.of[IO] {
    case GET -> Root / "hello" :? NameParamMatcher(name) => Ok(s"Hello, $name!")
  }

  // client
  val name = "Adam"
  sttp.get(uri"http://localhost:8080/hello?name=$name")

  // docs
  val yaml = """
openapi: 3.0.1
info:
  title: Hello world
  version: '1.0'
paths:
  /hello:
    get:
      operationId: getHello
      parameters:
      - name: name
        in: query
        required: true
        schema:
          type: string
      responses:
        '200':
          description: ''
          content:
            text/plain:
              schema:
                type: string"""
}
