package com.softwaremill.demo

import cats._
import cats.free.Free
import cats.free.Free.liftF

/**
  * Free monad in Scala:
  *   - base instruction set, reified as case classes
  *   - flatMap + pure
  *
  * 1. Create a description of a program
  * 2. Interpret it given an interpretation of the base instructions
  */
class S220_Free_monads {
  // From the cats tutorial: https://typelevel.org/cats/datatypes/freemonad.html
  // Base instruction set
  sealed trait KVStoreA[A]
  case class Put[T](key: String, value: T) extends KVStoreA[Unit]
  case class Get[T](key: String) extends KVStoreA[Option[T]]
  case class Delete(key: String) extends KVStoreA[Unit]

  // + flatMap / pure
  type KVStore[A] = Free[KVStoreA, A]

  // API
  def put[T](key: String, value: T): KVStore[Unit] = liftF[KVStoreA, Unit](Put[T](key, value))
  def get[T](key: String): KVStore[Option[T]] = liftF[KVStoreA, Option[T]](Get[T](key))
  def delete(key: String): KVStore[Unit] = liftF(Delete(key))

  // Description of a program
  def program: KVStore[Option[Int]] =
    for {
      _ <- put("wild-cats", 2)
      _ <- put("tame-cats", 5)
      n <- get[Int]("wild-cats")
      _ <- delete("tame-cats")
    } yield n

  // Interpreter
  def impureCompiler: KVStoreA ~> Id = ???
}
