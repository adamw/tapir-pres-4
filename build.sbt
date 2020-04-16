import sbt._
import Keys._

name := "tapir-pres-4"
organization := "com.softwaremill"
scalaVersion := "2.13.1"

val tapirVersion = "0.13.2"

libraryDependencies ++= Seq(
  "com.softwaremill.sttp.tapir" %% "tapir-core" % tapirVersion,
  "com.softwaremill.sttp.tapir" %% "tapir-akka-http-server" % tapirVersion,
  "com.softwaremill.sttp.tapir" %% "tapir-http4s-server" % tapirVersion,
  "com.softwaremill.sttp.tapir" %% "tapir-json-circe" % tapirVersion,
  "com.softwaremill.sttp.tapir" %% "tapir-openapi-docs" % tapirVersion,
  "com.softwaremill.sttp.tapir" %% "tapir-openapi-circe-yaml" % tapirVersion,
  "com.softwaremill.sttp.tapir" %% "tapir-sttp-client" % tapirVersion,
  "com.softwaremill.sttp.tapir" %% "tapir-swagger-ui-http4s" % tapirVersion,
  "com.softwaremill.sttp.tapir" %% "tapir-swagger-ui-akka-http" % tapirVersion,
  "ch.qos.logback" % "logback-core" % "1.2.3",
  "com.typesafe.scala-logging" %% "scala-logging" % "3.9.2",
  "javax.ws.rs" % "javax.ws.rs-api" % "2.1.1",
  "javax.xml.bind" % "jaxb-api" % "2.3.0",
  "com.typesafe.akka" %% "akka-http-spray-json" % "10.1.11",
  "org.http4s" %% "http4s-circe" % "0.21.3"
)

commonSmlBuildSettings
