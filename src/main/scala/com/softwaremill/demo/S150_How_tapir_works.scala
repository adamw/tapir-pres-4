package com.softwaremill.demo

import java.util.UUID

import javax.ws.rs.core.MediaType
import javax.ws.rs.{GET, Path, PathParam, Produces}
import org.http4s.{HttpRoutes, Request, Response}

class S150_How_tapir_works {
  // We want to describe a function:
  def f[I, O]: I => O = ???

  // Restricting ourselves to the HTTP domain:
  def g[F[_]]: Request[F] => F[Response[F]] = ???

  // But! we need to introspect the description
  def h[F[_]]: HttpRoutes[F] = ??? // not enough

  // Java does this through reflection & annotations:
  @GET
  @Path("/books/{id}")
  @Produces(Array(MediaType.APPLICATION_JSON))
  def getBookById[F[_]](@PathParam("id") id: UUID): Response[F] = ???

  // How to capture the structure?
}
