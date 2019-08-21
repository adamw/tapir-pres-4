package com.softwaremill.demo

class S280_Shape {
  // What's the shape of the function that we want to describe?

  // The most general:
  def f[I, O]: I => O = ???

  // The type of error outputs is almost always different from success, hence:
  def g[I, E, O]: I => Either[E, O] = ???

  // And indeed:
  case class Endpoint[I, E, O, +S]()
  // corresponds to the server function, for some F:
  def h[I, E, O, F[_]]: I => F[Either[E, O]] = ???
}
