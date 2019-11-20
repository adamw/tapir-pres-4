package com.softwaremill.demo

import cats.effect.IO
import org.http4s.EntityBody
import sttp.model.MultiQueryParams
import sttp.tapir._

class S360_Back_door {
  // A generic endpoint: just in case our data structures and API isn't rich enough

  val anyEndpoint: Endpoint[
    (Seq[(String, String)], MultiQueryParams, Seq[String], EntityBody[IO]),
    (Seq[(String, String)], Array[Byte]),
    (Seq[(String, String)], EntityBody[IO]),
    EntityBody[IO]
  ] = endpoint
    .in(headers)
    .in(queryParams)
    .in(paths)
    .in(streamBody[EntityBody[IO]](schemaFor[Array[Byte]], CodecFormat.OctetStream()))
    .errorOut(headers)
    .errorOut(binaryBody[Array[Byte]])
    .out(headers)
    .out(streamBody[EntityBody[IO]](schemaFor[Array[Byte]], CodecFormat.OctetStream()))
}
