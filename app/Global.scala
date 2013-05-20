import anorm.Id
import com.tzavellas.sse.guice.ScalaModule
import controllers.auth.SecurityBase
import models.User
import play.api._
import com.google.inject.Guice

object Global extends GlobalSettings {

  private lazy val injector = Guice.createInjector(new SecurityModule)

  override def getControllerInstance[A](clazz: Class[A]) = {
    injector.getInstance(clazz)
  }

  override def onStart(app: Application) {
    InitialData.insert()
    Logger.info("Application has started")
  }

  /**
   * Initial set of data to be imported
   * in the sample application.
   */
  object InitialData {

    def date(str: String) = new java.text.SimpleDateFormat("yyyy-MM-dd").parse(str)

    def insert() = {

      if(User.findAll.isEmpty) {

        Seq(
          User(Id(1), "myuser@gmail.com", "secret")
        ).foreach(User.create)

      }

    }

  }
  
  class SecurityImpl extends SecurityBase {
    type User = models.User

    def authenticate(username: String, password: String): Boolean = {
      User.authenticate(username, password) match {
        case Some(user:User) => true
        case _ => false
      }
    }
  }

  class SecurityModule extends ScalaModule {
    def configure() {
      bind[SecurityBase].to[SecurityImpl]
    }
  }

}
