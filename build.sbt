import Dependencies._

ThisBuild / scalaVersion := "2.12.14"
ThisBuild / version := "0.1.0-SNAPSHOT"
ThisBuild / organization := "exporter"
ThisBuild / organizationName := "exporter"

val circeVersion = "0.14.1"

lazy val root = (project in file("."))
  .settings(
    name := "scala-asana-csv-exporter",
    libraryDependencies += scalaTest % Test,
    libraryDependencies += sttp,
    libraryDependencies ++= Seq(
      "io.circe" %% "circe-core",
      "io.circe" %% "circe-generic",
      "io.circe" %% "circe-parser"
    ).map(_ % circeVersion),
    libraryDependencies += csv,
    libraryDependencies += nscalatime
  )
