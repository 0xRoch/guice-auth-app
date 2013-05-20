package controllers.auth

import play.api.mvc._
import com.google.inject._

class Application @Inject()(security: SecurityBase) extends Controller {
  
  def index = Action {
    security.authenticate("myuser@gmail.com", "secret") match {
      case true => Ok
      case _ => Forbidden
    }
  }
  
}

trait SecurityBase {
  def authenticate(username: String, password: String): Boolean
}