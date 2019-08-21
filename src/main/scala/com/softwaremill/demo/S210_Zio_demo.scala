package com.softwaremill.demo

import zio.clock.Clock
import zio.{DefaultRuntime, UIO, ZIO}
import zio.duration._

object S210_Zio_demo extends App {
  def effectful(): Unit = {
    println("Hello, world!")
  }

  val program: ZIO[Clock, Nothing, Nothing] = ZIO
    .effect(effectful())
    .flatMap(_ => ZIO.sleep(1.second))
    .catchAll(e => UIO(println(s"Exception: ${e.getMessage}")))
    .forever

  val runtime = new DefaultRuntime {}
  runtime.unsafeRun(program)
}
