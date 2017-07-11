import xerial.sbt.Sonatype

organization := "me.lessis"

name := "retry"

version := "0.2.1"

description := "a library of simple primitives for asynchronously retrying Scala Futures"

crossScalaVersions := Seq("2.11.11", "2.12.2")
scalaVersion in ThisBuild := crossScalaVersions.value.last

val commonSettings = lsSettings ++ Seq(
  LsKeys.tags in LsKeys.lsync := Seq("future", "retry"),
  scalacOptions += "-feature",
  resolvers += sbt.Resolver.bintrayRepo("softprops","maven")
)

lazy val retry = (crossProject in file ("."))
  .settings(commonSettings: _*)
  .settings(
    publishTo := {
      val nexus = "https://oss.sonatype.org/"
      if (isSnapshot.value)
        Some("snapshots" at nexus + "content/repositories/snapshots")
      else
        Some("releases" at nexus + "service/local/staging/deploy/maven2")
    },
    publishMavenStyle := true,
    publishArtifact in Test := false,
    pomIncludeRepository := { _ =>
      false
    },
    sonatypeProfileName := "de.kaufhof"
  )
  .jvmSettings(
    libraryDependencies ++=
      Seq("org.scalatest" %% "scalatest" % "3.0.1" % "test",
        "me.lessis" %% "odelay-core" % "0.2.0")
  )
  .jsSettings(
    libraryDependencies ++=
      Seq("org.scalatest" %%% "scalatest" % "3.0.2" % "test",
        "me.lessis" %%% "odelay-core" % "0.2.0")
  )


lazy val retryJs = retry.js
lazy val retryJvm = retry.jvm

licenses :=
  Seq("MIT" ->
      url(s"https://github.com/softprops/${name.value}/blob/${version.value}/LICENSE"))

homepage := Some(url(s"https://github.com/softprops/${name.value}/"))

publishArtifact in Test := false

publishMavenStyle := true

pomExtra := (
  <scm>
  <url>git@github.com:softprops/retry.git</url>
  <connection>scm:git:git@github.com:softprops/retry.git</connection>
  </scm>
  <developers>
  <developer>
  <id>softprops</id>
  <name>Doug Tangren</name>
    <url>http://github.com/softprops</url>
  </developer>
  </developers>)

