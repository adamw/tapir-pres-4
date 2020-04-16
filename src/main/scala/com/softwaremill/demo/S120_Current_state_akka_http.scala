package com.softwaremill.demo

import akka.http.scaladsl.marshallers.sprayjson.SprayJsonSupport
import akka.http.scaladsl.server.Route
import akka.http.scaladsl.server.Directives._
import spray.json._

object S120_Current_state_akka_http extends SprayJsonSupport with DefaultJsonProtocol {
  case class Book(title: String)

  implicit val bookFormat: RootJsonFormat[Book] = jsonFormat1(Book)

  val route: Route = get {
    path("books" / Segment) { id =>
      complete(Book("The Trial"))
    }
  }
}
