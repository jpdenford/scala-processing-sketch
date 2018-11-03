import Dependencies._

val processingVersion = "3.3.6"

lazy val root = (project in file(".")).
  settings(
    inThisBuild(List(
      organization := "com.example",
      scalaVersion := "2.12.7",
      version      := "0.1.0-SNAPSHOT"
    )),
    name := "untitled",
    libraryDependencies ++= Seq(
      scalaTest % Test,
      "org.processing" % "core" % "3.3.6"
    )

)
