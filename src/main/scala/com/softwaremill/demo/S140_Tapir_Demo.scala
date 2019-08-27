package com.softwaremill.demo

import java.util.UUID

import io.circe.generic.auto._
import tapir.json.circe._

object S140_Tapir_Demo extends App {
  case class Year(year: Int)
  case class Book(id: UUID, title: String, year: Year)
  val exampleBook = Book(UUID.randomUUID(), "Lords and Ladies", Year(1992))

  import tapir._

  // GET /books?year=...&limit=... (parameters optional) -> json list of books
  val getBooksEndpoint: Endpoint[(Option[Int], Option[Int]), String, List[Book], Nothing] = endpoint.get
    .in("books")
    .in(query[Option[Int]]("year"))
    .in(query[Option[Int]]("limit"))
    .errorOut(stringBody)
    .out(jsonBody[List[Book]])

  // GET /book/c5e41285-a229-419a-93f3-1a834842b352 -> json book
  val getBookEndpoint: Endpoint[UUID, String, Book, Nothing] = endpoint.get
    .in("books")
    .in(path[UUID]("bookId"))
    .errorOut(stringBody)
    .out(jsonBody[Book].example(exampleBook))
}
