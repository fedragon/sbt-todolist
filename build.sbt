

sbtPlugin := true

organization := "com.github.fedragon"

name := "sbt-todolist"

description := "Dead simple SBT plugin to find tags in source files and print them to the console"

homepage := Some(url("https://github.com/fedragon/sbt-todolist"))

publishMavenStyle := true

publishTo := {
  version.value.trim match {
    case snapshot if snapshot.endsWith("SNAPSHOT") => Some(Resolver.sonatypeRepo("snapshots"))
    case _ => Some(Resolver.sonatypeRepo("releases"))
  }
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
