package com.softwaremill.demo

import cats.data.NonEmptyList
import org.http4s.{Charset, ContentCoding, MediaType}

class S350_Type_safe_header_model {
  // How to model headers (and other HTTP entities)?

  // Type-safe? As in http4s, akka-http
  case class `Accept-Encoding`(values: NonEmptyList[ContentCoding])
  case class `Content-Type` private (mediaType: MediaType, charset: Option[Charset])

  // Seems like a great idea!
  // But, not so great to work with: tricky imports, sending simple values
  //   (e.g. how to set Content-Type to json)

  // Alternative:
  trait HeaderNames {
    val Accept = "Accept"
    val AcceptEncoding = "Accept-Encoding"
    val Authorization = "Authorization"
    val ContentLength = "Content-Length"
    val ContentType = "Content-Type"
    // ...
  }

  // Still on the fence
}
