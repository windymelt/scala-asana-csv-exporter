import sbt._

object Dependencies {
  lazy val scalaTest = "org.scalatest" %% "scalatest" % "3.0.5"
  lazy val sttp = "com.softwaremill.sttp.client3" %% "core" % "3.3.7"
  lazy val csv = "com.github.tototoshi" %% "scala-csv" % "1.3.8"
  lazy val nscalatime = "com.github.nscala-time" %% "nscala-time" % "2.28.0"
  lazy val googleApiClient = "com.google.api-client" % "google-api-client" % "1.30.4"
  lazy val googleOAuthClient = "com.google.oauth-client" % "google-oauth-client-jetty" % "1.30.6"
  lazy val googleSpreadSheetApi = "com.google.apis" % "google-api-services-sheets" % "v4-rev581-1.25.0"

}
