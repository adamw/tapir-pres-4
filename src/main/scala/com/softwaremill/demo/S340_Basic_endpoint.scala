package com.softwaremill.demo

class S340_Basic_endpoint {
/*
An empty input: () (unit)
An empty output: () (also unit)
An empty endpoint:
 takes no inputs, returns 200 OK. What's the type? Unit => F[Either[?, Unit]]
Should we always allow for errors? Endpoint[Unit, Unit, Unit]
 nice, symmetric
Or should there be no errors by default? Endpoint[Unit, Nothing, Unit]
 you can always get a 500 Internal Server Error
 what about Endpoint[Unit, Unit, Nothing]?
 */
}
