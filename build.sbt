import sbt._
import Keys._

name := "tapir-pres-3"
organization := "com.softwaremill"
scalaVersion := "2.12.9"

val tapirVersion = "0.9.3"

libraryDependencies ++= Seq(
  "com.softwaremill.tapir" %% "tapir-core" % tapirVersion,
  "com.softwaremill.tapir" %% "tapir-http4s-server" % tapirVersion,
  "com.softwaremill.tapir" %% "tapir-json-circe" % tapirVersion,
  "com.softwaremill.tapir" %% "tapir-openapi-docs" % tapirVersion,
  "com.softwaremill.tapir" %% "tapir-openapi-circe-yaml" % tapirVersion,
  "com.softwaremill.tapir" %% "tapir-sttp-client" % tapirVersion,
  "com.softwaremill.tapir" %% "tapir-swagger-ui-http4s" % tapirVersion,
  "dev.zio" %% "zio" % "1.0.0-RC11-1",
  "org.tpolecat" %% "doobie-core" % "0.7.0",
  "ch.qos.logback" % "logback-core" % "1.2.3",
  "com.typesafe.scala-logging" %% "scala-logging" % "3.9.2",
  "javax.ws.rs" % "javax.ws.rs-api" % "2.1.1"
)

commonSmlBuildSettings
