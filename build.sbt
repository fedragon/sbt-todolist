import Defaults._

sbtPlugin := true

organization := "com.github.fedragon"

name := "sbt-todolist"

description := "Dead simple SBT plugin to find tags in source files and print them to the console"

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
  <licenses>
    <license>
      <name>MIT</name>
      <url>https://github.com/fedragon/sbt-todolist/blob/master/LICENSE</url>
    </license>
  </licenses>
  <scm>
    <connection>scm:git:github.com/fedragon/sbt-todolist</connection>
    <developerConnection>scm:git:git@github.com:fedragon/sbt-todolist</developerConnection>
    <url>github.com/fedragon/sbt-todolist</url>
  </scm>
  <developers>
    <developer>
      <id>fedragon</id>
      <name>Federico Ragona</name>
    </developer>
  </developers>
)
