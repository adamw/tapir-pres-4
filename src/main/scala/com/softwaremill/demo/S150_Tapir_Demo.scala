package com.softwaremill.demo

object S150_Tapir_Demo extends App {
  case class Book(title: String, year: Int)

  var books = List(
    Book("The Sorrows of Young Werther", 1774),
    Book("Iliad", -8000),
    Book("Nad Niemnem", 1888),
    Book("The Colour of Magic", 1983),
    Book("The Art of Computer Programming", 1968),
    Book("Pharaoh", 1897),
    Book("Lords and Ladies", 1992)
  )

  object Endpoints {
    import io.circe.generic.auto._
    import sttp.tapir._
    import sttp.tapir.json.circe._

    // All endpoints report errors as strings, and have the common path prefix '/books'
    val baseEndpoint: Endpoint[Unit, String, Unit, Any] = endpoint.errorOut(stringBody).in("books")

    // POST /books
    val addBook: Endpoint[(Book, String), String, Unit, Any] = baseEndpoint.post
      .in("add")
      .in(
        jsonBody[Book]
          .description("The book to add")
          .example(Book("Pride and Prejudice", 1813))
      )
      .in(header[String]("X-Auth-Token").description("The token is 'secret'"))

    // Re-usable parameter description
    val yearParameter: EndpointInput[Option[Int]] = query[Option[Int]]("year").description("The year from which to retrieve books")
    val limitParameter: EndpointInput[Option[Int]] = query[Option[Int]]("limit").description("Maximum number of books to retrieve")

    // GET /books
    val booksListing: Endpoint[(Option[Int], Option[Int]), String, List[Book], Any] = baseEndpoint.get
      .in(yearParameter)
      .in(limitParameter)
      .out(jsonBody[List[Book]])
  }

  //

  import Endpoints._
  import akka.http.scaladsl.server.Route

  def booksRoutes: Route = {
    import akka.http.scaladsl.server.Directives._
    import sttp.tapir.server.akkahttp._

    import scala.concurrent.ExecutionContext.Implicits.global
    import scala.concurrent.Future

    def bookAddLogic(book: Book, token: String): Future[Either[String, Unit]] = Future {
      if (token != "secret") {
        Left("Unauthorized access!!!11")
      } else {
        books = book :: books
        Right(())
      }
    }

    def bookListingLogic(year: Option[Int], limit: Option[Int]): Future[Either[String, List[Book]]] = Future {
      val filteredBooks = year match {
        case None    => books
        case Some(y) => books.filter(_.year == y)
      }
      val limitedBooks = limit match {
        case None    => filteredBooks
        case Some(l) => filteredBooks.take(l)
      }
      Right(limitedBooks)
    }

    // interpreting the endpoint description and converting it to an akka-http route, providing the logic which
    // should be run when the endpoint is invoked.
    addBook.toRoute((bookAddLogic _).tupled) ~ booksListing.toRoute((bookListingLogic _).tupled)
  }

  def openapiYamlDocumentation: String = {
    import sttp.tapir.docs.openapi._
    import sttp.tapir.openapi.circe.yaml._

    // interpreting the endpoint description to generate yaml openapi documentation
    val docs = List(addBook, booksListing).toOpenAPI("Books I've read", "1.0")
    docs.toYaml
  }

  def startServer(): Unit = {
    import akka.actor.ActorSystem
    import akka.http.scaladsl.Http
    import akka.http.scaladsl.server.Directives._
    import sttp.tapir.swagger.akkahttp.SwaggerAkka

    import scala.concurrent.Await
    import scala.concurrent.duration._

    val routes = booksRoutes ~ new SwaggerAkka(openapiYamlDocumentation).routes
    implicit val actorSystem: ActorSystem = ActorSystem()

    Await.result(Http().newServerAt("localhost", 8080).bind(routes), 1.minute)
  }

  def makeClientRequest(): Unit = {
    import sttp.client3._
    import sttp.tapir.client.sttp._

    val backend: SttpBackend[Identity, Any] = HttpURLConnectionBackend()

    val booksListingRequest: Request[Either[String, List[Book]], Any] = booksListing
      .toSttpRequestUnsafe(uri"http://localhost:8080")
      .apply(None, Option(3))

    val result: Either[String, List[Book]] = booksListingRequest.send(backend).body
    println("Client call result: " + result)
  }

  startServer()
  makeClientRequest()
  println("Try out the API by opening the Swagger UI: http://localhost:8080/docs")
}
