package models

import play.api.db._
import play.api.Play.current
import anorm._
import anorm.SqlParser._

case class User(id: Pk[Long], username: String, password: String)

object User {

  // -- Parsers

  /**
   * Parse a User from a ResultSet
   */
  val simple = {
      get[Pk[Long]]("user.id") ~
      get[String]("user.username") ~
      get[String]("user.password") map {
      case id~username~password => User(id, username, password)
    }
  }

  // -- Queries

  /**
   * Retrieve a User from the id.
   */
  def findById(id: Long): Option[User] = {
    DB.withConnection { implicit connection =>
      SQL("select * from user where id = {id}").on(
        'id -> id
      ).as(User.simple.singleOpt)
    }
  }

  /**
   * Retrieve a User from email.
   */
  def findByUsername(username: String): Option[User] = {
    DB.withConnection { implicit connection =>
      SQL("select * from user where username = {username}").on(
        'username -> username
      ).as(User.simple.singleOpt)
    }
  }

  /**
   * Retrieve all users.
   */
  def findAll: Seq[User] = {
    DB.withConnection { implicit connection =>
      SQL("select * from user").as(User.simple *)
    }
  }

  /**
   * Authenticate a User.
   */
  def authenticate(username: String, password: String): Option[User] = {
    DB.withConnection { implicit connection =>
      SQL(
        """
         select * from user where 
         username = {username} and password = {password}
        """
      ).on(
        'username -> username,
        'password -> password
      ).as(User.simple.singleOpt)
    }
  }

  /**
   * Create a User.
   */
  def create(user: User): User = {
    DB.withConnection { implicit connection =>
      SQL(
        """
          insert into user values (
            {id}, {username}, {password}
          )
        """
      ).on(
        'id -> user.id,
        'username -> user.username,
        'password -> user.password
      ).executeUpdate()

      user

    }
  }

}
