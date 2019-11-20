import sbt._
import Keys._

name := "tapir-pres-3"
organization := "com.softwaremill"
scalaVersion := "2.12.10"

val tapirVersion = "0.12.2"

libraryDependencies ++= Seq(
  "com.softwaremill.sttp.tapir" %% "tapir-core" % tapirVersion,
  "com.softwaremill.sttp.tapir" %% "tapir-http4s-server" % tapirVersion,
  "com.softwaremill.sttp.tapir" %% "tapir-json-circe" % tapirVersion,
  "com.softwaremill.sttp.tapir" %% "tapir-openapi-docs" % tapirVersion,
  "com.softwaremill.sttp.tapir" %% "tapir-openapi-circe-yaml" % tapirVersion,
  "com.softwaremill.sttp.tapir" %% "tapir-sttp-client" % tapirVersion,
  "com.softwaremill.sttp.tapir" %% "tapir-swagger-ui-http4s" % tapirVersion,
  "dev.zio" %% "zio" % "1.0.0-RC17",
  "org.tpolecat" %% "doobie-core" % "0.8.6",
  "ch.qos.logback" % "logback-core" % "1.2.3",
  "com.typesafe.scala-logging" %% "scala-logging" % "3.9.2",
  "javax.ws.rs" % "javax.ws.rs-api" % "2.1.1"
)

commonSmlBuildSettings
