package com.softwaremill.demo

import java.util.UUID

import io.circe.generic.auto._
import tapir.json.circe._

object S140_Tapir_Demo extends App {
  case class Year(year: Int)
  case class Book(id: UUID, title: String, year: Year)
  val exampleBook = Book(UUID.randomUUID(), "Lords and Ladies", Year(1992))

  import tapir._

  val getBooksEndpoint: Endpoint[(Option[Int], Option[Int]), String, List[Book], Nothing] = endpoint.get
    .in("books")
    .in(query[Option[Int]]("year"))
    .in(query[Option[Int]]("limit"))
    .errorOut(stringBody)
    .out(jsonBody[List[Book]])

  val getBookEndpoint: Endpoint[UUID, String, Book, Nothing] = endpoint.get
    .in("books")
    .errorOut(stringBody)
    .in(path[UUID]("bookId"))
    .out(jsonBody[Book].example(exampleBook))
}
