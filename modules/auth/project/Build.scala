import sbt._
import Keys._
import play.Project._

object ApplicationBuild extends Build {

  val appName         = "auth"
  val appVersion      = "1.0-SNAPSHOT"

  val appDependencies = Seq(
	"com.google.inject" % "guice" % "3.0",
    "com.tzavellas" % "sse-guice" % "0.7.1"
  )
  
  val main = play.Project(appName, appVersion, appDependencies).settings(
    // Add your own project settings here      
  )

}
