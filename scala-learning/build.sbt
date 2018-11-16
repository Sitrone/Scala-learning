name := "scala-learning"

version := "0.1"

scalaVersion := "2.12.6"

libraryDependencies ++= Seq(
  "com.typesafe.akka" %% "akka-actor" % "2.5.11",
  "com.typesafe.akka" %% "akka-remote" % "2.5.11",
  "org.scalaj" %% "scalaj-http" % "2.3.0",
  "io.spray" %% "spray-json" % "1.3.4",
  "com.typesafe.akka" %% "akka-testkit" % "2.5.11" % Test,
  "org.scalatest" %% "scalatest" % "3.0.5" % "test"
)