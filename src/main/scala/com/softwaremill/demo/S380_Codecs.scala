package com.softwaremill.demo

class S380_Codecs {
  /*
A String-codec for text/plain and application/json is different
A query parameter always needs a text/plain codec
Body might need a json, a or a text codec
  this information is lost in the signature
  hence, codecs are required when describing the endpoint
   */
}
