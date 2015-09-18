package com.github.fedragon

import sbt._
import Keys._

object TodoListPlugin extends AutoPlugin {
  object autoImport {
    lazy val todolist = taskKey[Unit]("Finds and prints 'work in progress' tags (TODO, FIXME, ...) to the console")
    lazy val todolistTags = settingKey[Set[String]]("Tags to look for")
  }

  import autoImport._

  // enable automatically this plugin, without needing to `enablePlugins` in a project's build.sbt
  override def trigger = allRequirements

  // add the task and a set of default tags
  override def projectSettings = Seq(
    todolistTags := Set("FIXME", "TODO", "WIP", "XXX"),
    todolist := {
      TodoList((sources in Compile).value ++ (sources in Test).value, (todolistTags in todolist).value)
    }
  )
}

object TodoList {
  import scala.io.Source

  def apply(sources: Seq[File], tags: Set[String]): Unit = {
    sources.foreach { f =>
      val todos = Source.fromFile(f).getLines.zipWithIndex.flatMap {
        case (line, index) =>
          if(tags.exists(line.contains)) Some((line, index + 1))
          else None
      }

      todos.foreach {
        case (line, index) =>
          println(s"${f.getName}:$index $line")
      }
    }
  }
}
