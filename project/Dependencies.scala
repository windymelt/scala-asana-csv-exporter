import sbt._

object Dependencies {
  lazy val scalaTest = "org.scalatest" %% "scalatest" % "3.0.5"
  lazy val sttp = "com.softwaremill.sttp.client3" %% "core" % "3.3.7"
  lazy val csv = "com.github.tototoshi" %% "scala-csv" % "1.3.8"
  lazy val nscalatime = "com.github.nscala-time" %% "nscala-time" % "2.28.0"

}
