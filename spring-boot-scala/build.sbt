name := "spring-boot-scala"
version := "0.1"
scalaVersion := "2.12.6"

javacOptions ++= Seq("-source", "1.8", "-target", "1.8")

lazy val springBootVersion = "2.0.6.RELEASE"

libraryDependencies ++= Seq(
  "org.springframework.boot" % "spring-boot" % springBootVersion,
  "org.springframework.boot" % "spring-boot-starter-web" % springBootVersion,
  "org.springframework.boot" % "spring-boot-starter-actuator" % springBootVersion,
  "org.springframework.boot" % "spring-boot-autoconfigure" % springBootVersion,
  "org.springframework.boot" % "spring-boot-starter-logging" % springBootVersion,
  "org.log4s" %% "log4s" % "1.6.1",
  "com.fasterxml.jackson.module" %% "jackson-module-scala" % "2.9.6",
  "org.json4s" %% "json4s-jackson" % "3.6.1",
  "org.scalatest" %% "scalatest" % "3.0.5" % Test,
  "org.scalamock" %% "scalamock" % "4.1.0" % Test,
  "org.springframework.boot" % "spring-boot-starter-test" % springBootVersion % Test,
  "junit" % "junit" % "4.12" % Test,
)

mainClass := Some("com.sununiq.scala.Application")
enablePlugins(JavaAppPackaging)