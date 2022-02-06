import Dependencies._

ThisBuild / organization := "org.grauulzz"
ThisBuild / scalaVersion := "3.1.0"

Test / compileOrder := CompileOrder.Mixed
Compile / compileOrder := CompileOrder.Mixed

ThisBuild / scalacOptions ++=
  Seq(
    "-deprecation",
    "-feature",
    "-language:implicitConversions",
    "-unchecked",
    "-Xfatal-warnings",
    "-Yexplicit-nulls", // experimental (I've seen it cause issues with circe)
    "-Ykind-projector",
    "-Ysafe-init", // experimental (I've seen it cause issues with circe)
  ) ++ Seq("-old-syntax", "-rewrite") ++ Seq("-source", "future")

ThisBuild / javacOptions ++=
  Seq(
    "-source",  // Known issues in mixed mode compilation: https://www.scala-sbt.org/1.x/docs/Java-Sources.html
    "11"
  )

lazy val `audio-write` =
  project
    .in(file("."))
    .settings(name := "audio-write")
    .settings(commonSettings)
    .settings(dependencies)

lazy val commonSettings = commonScalacOptions ++ Seq(
  update / evictionWarningOptions := EvictionWarningOptions.empty
)

lazy val commonScalacOptions = Seq(
  Compile / console / scalacOptions --= Seq(
    "-Wunused:_",
    "-Xfatal-warnings",
  ),
  Test / console / scalacOptions :=
    (Compile / console / scalacOptions).value,
)

lazy val dependencies = Seq(
  libraryDependencies ++= Seq(
    "de.sciss" %% "scalacollider" % "2.7.4",
    "de.sciss" %% "scalacolliderswing" % "2.9.2"
  ),
  libraryDependencies ++= Seq(
    org.scalatest.scalatest,
    org.scalatestplus.`scalacheck-1-15`,
  ).map(_ % Test),
)
