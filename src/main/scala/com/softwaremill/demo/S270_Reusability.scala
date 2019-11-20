package com.softwaremill.demo

import java.util.UUID

import com.softwaremill.demo.S140_Tapir_Demo.Book
import sttp.tapir._
import sttp.tapir.json.circe._
import io.circe.generic.auto._
import sttp.model.StatusCode

class S270_Reusability {
  // Incremental builder
  // Immutable data structures
  // Store as a value, use anywhere

  // Abstraction!

  case class ErrorInfo(statusCode: StatusCode, msg: String)
  case class BookFilter(year: Option[Int], limit: Option[Int])

  object V1 {
    val getBooksEndpoint: Endpoint[BookFilter, ErrorInfo, List[Book], Nothing] = endpoint.get
      .in("books")
      .errorOut(statusCode.and(stringBody).mapTo(ErrorInfo))
      .in(query[Option[Int]]("fromYear").and(query[Option[Int]]("limit")).mapTo(BookFilter))
      .out(jsonBody[List[Book]])

    val getBookEndpoint: Endpoint[UUID, ErrorInfo, Book, Nothing] = endpoint.get
      .in("books")
      .errorOut(statusCode.and(stringBody).mapTo(ErrorInfo))
      .in(path[UUID]("bookId"))
      .out(jsonBody[Book])
  }

  object V2 {
    val bookFilterInput: EndpointInput[BookFilter] =
      query[Option[Int]]("fromYear")
        .and(query[Option[Int]]("limit"))
        .mapTo(BookFilter)

    val errorInfoOutput: EndpointOutput[ErrorInfo] = statusCode.and(stringBody).mapTo(ErrorInfo)

    val baseEndpoint: Endpoint[Unit, ErrorInfo, Unit, Nothing] = endpoint
      .in("books")
      .errorOut(errorInfoOutput)

    val getBooksEndpoint: Endpoint[BookFilter, ErrorInfo, List[Book], Nothing] = baseEndpoint.get
      .in(bookFilterInput)
      .out(jsonBody[List[Book]])

    val getBookEndpoint: Endpoint[UUID, ErrorInfo, Book, Nothing] = baseEndpoint.get
      .in(path[UUID]("bookId"))
      .out(jsonBody[Book])
  }
}
