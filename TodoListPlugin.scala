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

  // redefine an existing task, adding `todolist` to its dependencies
  def withTodolistSettings[T](t: TaskKey[T], c: Configuration): Seq[Project.Setting[_]] =
    inConfig(c)((t in c) <<= (t in c).dependsOn(todolist))

  val compileWithTodolistSettings: Seq[Project.Setting[_]] =
    withTodolistSettings(compile, Compile)

  val testWithTodolistSettings: Seq[Project.Setting[_]] =
    withTodolistSettings(test, Test)
}

object TodoList {
  import scala.io.Source

  def apply(sources: Seq[File], tags: Set[String]): Unit = {
    sources.foreach { f =>
      val bs = Source.fromFile(f)
      try {
        val todos = bs.getLines.zipWithIndex.flatMap {
          case (line, index) =>
            if(tags.exists(line.contains)) Some((line, index + 1))
            else None
        }

        todos.foreach {
          case (line, index) =>
            println(s"${f.getName}:$index $line")
        }
      } finally {
        bs.close()
      }
    }
  }
}
