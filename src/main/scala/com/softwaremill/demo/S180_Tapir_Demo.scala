package com.softwaremill.demo

import java.util.UUID

import cats.implicits._
import cats.effect.{ContextShift, IO, Timer}
import io.circe.generic.auto._
import org.http4s.HttpRoutes
import org.http4s.server.Router
import org.http4s.server.blaze.BlazeServerBuilder
import org.http4s.syntax.kleisli._
import tapir.json.circe._
import tapir.openapi.OpenAPI
import tapir.swagger.http4s.SwaggerHttp4s

import scala.concurrent.ExecutionContext

object S180_Tapir_Demo extends App {
  case class Year(year: Int)
  case class Book(id: UUID, title: String, year: Year)

  val books = List(
    Book(UUID.randomUUID(), "Lords and Ladies", Year(1992)),
    Book(UUID.randomUUID(), "The Sorrows of Young Werther", Year(1774)),
    Book(UUID.randomUUID(), "Iliad", Year(-8000)),
    Book(UUID.randomUUID(), "Nad Niemnem", Year(1888)),
    Book(UUID.randomUUID(), "The Pelican Brief", Year(1992)),
    Book(UUID.randomUUID(), "The Art of Computer Programming", Year(1968)),
    Book(UUID.randomUUID(), "The English Patient", Year(1992)),
    Book(UUID.randomUUID(), "Pharaoh", Year(1897))
  )

  import tapir._

  // GET /books?year=...&limit=... (parameters optional) -> json list of books
  val getBooksEndpoint: Endpoint[(Option[Int], Option[Int]), String, List[Book], Nothing] = endpoint.get
    .in("books")
    .in(query[Option[Int]]("year"))
    .in(query[Option[Int]]("limit"))
    .errorOut(stringBody)
    .out(jsonBody[List[Book]])

  // GET /book/c5e41285-a229-419a-93f3-1a834842b352 -> json book
  val getBookEndpoint = endpoint.get
    .in("books")
    .errorOut(stringBody)
    .in(path[UUID]("bookId"))
    .out(jsonBody[Book].example(books.head))

  //

  import tapir.server.http4s._

  implicit val ec: ExecutionContext = scala.concurrent.ExecutionContext.Implicits.global
  implicit val contextShift: ContextShift[IO] = IO.contextShift(ec)
  implicit val timer: Timer[IO] = IO.timer(ec)

  val getBooksRoute: HttpRoutes[IO] = getBooksEndpoint.toRoutes {
    case (year, limit) =>
      val books1 = year.map(year => books.filter(_.year.year == year)).getOrElse(books)
      val books2 = limit.map(limit => books1.take(limit)).getOrElse(books1)
      IO(Right(books2))
  }

  val getBookRoute: HttpRoutes[IO] = getBookEndpoint.toRoutes { bookId =>
    books.find(_.id == bookId) match {
      case Some(book) => IO(Right(book))
      case None       => IO(Left(s"Book with id $bookId not found!"))
    }
  }

  //

  import tapir.docs.openapi._
  import tapir.openapi.circe.yaml._

  val docs: OpenAPI = List(getBooksEndpoint, getBookEndpoint).toOpenAPI("The tapir library", "1.0")
  val yaml: String = docs.toYaml

  //

  val routes: HttpRoutes[IO] = getBooksRoute <+> getBookRoute

  // starting the server
  BlazeServerBuilder[IO]
    .bindHttp(8080, "localhost")
    .withHttpApp(Router("/" -> routes, "/docs" -> new SwaggerHttp4s(yaml).routes[IO]).orNotFound)
    .resource
    .use { _ =>
      IO {
        println("Go to: http://localhost:8080/docs")
        println("Press any key to exit ...")
        scala.io.StdIn.readLine()
      }
    }
    .unsafeRunSync()
}
