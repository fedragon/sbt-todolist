import Defaults._

sbtPlugin := true

organization := "com.github.fedragon"

name := "sbt-todolist"

description := "A dead simple SBT plugin to find tags in source files and print them to the console"

libraryDependencies += "org.scala-sbt" % "sbt" % "0.13.9"

homepage := Some(url("https://github.com/fedragon/sbt-todolist"))

publishMavenStyle := true

publishTo <<= version { (v: String) =>
  val nexus = "https://oss.sonatype.org/"
  if (v.trim.endsWith("SNAPSHOT")) Some(
    "snapshots" at nexus + "content/repositories/snapshots"
  )
  else Some("releases" at nexus + "service/local/staging/deploy/maven2")
}

publishArtifact in Test := false

pomIncludeRepository := { _ => false }

pomExtra := (
  <developers>
    <developer>
      <id>fedragon</id>
      <name>Federico Ragona</name>
    </developer>
  </developers>
)
