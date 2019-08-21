package com.softwaremill.demo

class S310_Shape_of_output {
  /*
Top-level coproduct: Either[E, O]
Otherwise, basic values & products (as in inputs)
Additional case: coproduct based on status code
  client: the status code serves as a discriminator, we know which branch to use
  server: -> code
    isInstanceOf (unfortunately)
    no exhaustiveness
   */
}
