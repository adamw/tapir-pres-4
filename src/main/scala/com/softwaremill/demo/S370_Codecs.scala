package com.softwaremill.demo

import doobie.util.Meta
import io.circe.{Decoder, Encoder}
import sttp.tapir.Codec
import sttp.tapir.Codec.PlainCodec

class S370_Codecs {
  // Many libraries need to encode/decode values!

  // Circe:
  implicit lazy val bigDecimalEncoder: Encoder[BigDecimal] = Encoder.encodeString.contramap(_.toString())
  implicit lazy val bigDecimalDecoder: Decoder[BigDecimal] = Decoder.decodeString.map { BigDecimal.apply }

  // Doobie:
  implicit val btcMeta: Meta[BigDecimal] = Meta[String].timap[BigDecimal](BigDecimal.apply)(_.toString())

  // Tapir is no different!
  implicit val bigDecimalPlainCodec: PlainCodec[BigDecimal] = Codec.stringPlainCodecUtf8.map(BigDecimal.apply)(_.toString())

  // One-way, or two-way?
  //   in tapir, two-way, because you never know how the endpoint is going to be interpreted

  // When to capture the implicit codecs?
  //   (a) when the endpoint is described
  //   (b) when the endpoint is interpreted
}
