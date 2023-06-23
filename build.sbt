ThisBuild / scalaVersion := "2.13.10"

val logback2Version = "1.4.7"
val zioVersion = "2.0.15"
val zioLoggingVersion = "2.1.13"

lazy val commonSettings = Seq(
  addCompilerPlugin("org.typelevel" %% "kind-projector" % "0.13.2" cross CrossVersion.full),
  scalacOptions ++= Seq("-language:higherKinds", "-Ydelambdafy:inline", "-deprecation", "-encoding", "UTF-8", "-feature", "-unchecked")
)

lazy val `zio-logging-app` =
  (project in file(".")).aggregate(`core`, `slf4j`, `slf4jBridge`)

lazy val core = (project in file("core")).settings(
  name := "core-app",
  commonSettings,
  libraryDependencies ++= Seq("dev.zio" %% "zio" % zioVersion, "dev.zio" %% "zio-logging" % zioLoggingVersion)
)

lazy val slf4j = (project in file("slf4j")).settings(
  name := "slf4j-app",
  commonSettings,
  libraryDependencies ++= Seq(
    "dev.zio" %% "zio" % zioVersion,
    "dev.zio" %% "zio-logging-slf4j2" % zioLoggingVersion,
    "ch.qos.logback" % "logback-classic" % logback2Version
  )
)

lazy val slf4jBridge = (project in file("slf4j-bridge")).settings(
  name := "slf4j-bridge-app",
  commonSettings,
  libraryDependencies ++= Seq("dev.zio" %% "zio" % zioVersion, "dev.zio" %% "zio-logging-slf4j2-bridge" % zioLoggingVersion)
)
