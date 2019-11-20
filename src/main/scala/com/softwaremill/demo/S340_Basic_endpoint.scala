package com.softwaremill.demo

import sttp.tapir._

class S340_Basic_endpoint {
  // An empty input: () (unit)
  // An empty output: () (also unit)

  // An empty endpoint:
  //   takes no inputs, returns 200 OK
  //   what's the type of the server function?
  //     Unit => F[Either[?, Unit]]

  // Should we always allow for errors?
  val e: Endpoint[Unit, Unit, Unit, Nothing] = endpoint
  // symmetric :)

  // Should there be no errors by default?
  val e1: Endpoint[Unit, Nothing, Unit, Nothing] = infallibleEndpoint
  // but ... you can always get a 500 Internal Server Error for client calls

  // What about?
  val e2: Endpoint[Unit, Unit, Nothing, Nothing] = ???
}
