package com.softwaremill.demo

import java.util.UUID

import doobie._
import doobie.implicits._
import cats.effect.IO
import cats.implicits._

class S200_Doobie_demo {
  def insertUser(id: Int, username: String): ConnectionIO[Unit] =
    sql"INSERT INTO users (id, username) VALUES ($id, $username)".update.run.void

  def makeAdmin(userId: Int): ConnectionIO[Unit] =
    sql"INSERT INTO admins (user_id) VALUES ($userId)".update.run.void

  def countUsers(): ConnectionIO[Int] =
    sql"SELECT COUNT(*) FROM users".query[Int].unique

  val userId: Int = 21
  val insertAdminAndCount: ConnectionIO[Int] = insertUser(userId, "Adam") >> makeAdmin(userId) >> countUsers

  val xa: Transactor[IO] = ???
  val result: IO[Int] = insertAdminAndCount.transact(xa)
}
