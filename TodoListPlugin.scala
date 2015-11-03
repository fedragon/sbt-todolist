package com.github.fedragon.todolist

import sbt._
import Keys._
import scala.util.matching.Regex

object TodoListPlugin extends AutoPlugin {
  object autoImport {
    lazy val todos = taskKey[Unit]("Finds and prints 'work in progress' tags (TODO, FIXME, ...) to the console")
    lazy val todosTags = settingKey[Set[String]]("Tags to look for")
  }

  import autoImport._

  // enable automatically this plugin, without needing to `enablePlugins` in a project's build.sbt
  override def trigger = allRequirements

  // add the task and a set of default tags
  override def projectSettings = Seq(
    todosTags := Set("FIXME", "TODO", "WIP", "XXX"),
    todos := {
      TodoList(baseDirectory.value, (sources in Compile).value ++ (sources in Test).value, (todosTags in todos).value)
    }
  )

  // redefine an existing task, adding `todolist` to its dependencies
  def withTodolistSettings[T](t: TaskKey[T], c: Configuration): Seq[Project.Setting[_]] =
    inConfig(c)((t in c) <<= (t in c).dependsOn(todos))

  val compileWithTodolistSettings: Seq[Def.Setting[_]] =
    withTodolistSettings(compile, Compile)

  val testWithTodolistSettings: Seq[Def.Setting[_]] =
    withTodolistSettings(test, Test)
}

object TodoList {
  import scala.Console._
  import scala.io.Source

  private def highlight(msg: String, color: String) =
    s"$color$msg$RESET"

  def apply(base: File, sources: Seq[File], tags: Set[String]): Unit = {
    val regexes = tags.map(t => new Regex(s"""(?i).*${t}(\\z|[\\s|:]+.*)"""))

    sources.foreach { file =>
      val bs = Source.fromFile(file)
      try {
        val todos = bs.getLines.zipWithIndex.flatMap {
          case (line, index) =>
            if(regexes.exists(_.findFirstMatchIn(line).isDefined)) Some((line, index + 1))
            else None
        }

        val relativeFileName = base.relativize(file).map(_.toString).getOrElse("Something went wrong! Cannot get relative path for ${file.getName}")

        todos.foreach {
          case (line, index) =>
            val f = highlight(relativeFileName, BLUE)
            val i = highlight(index.toString, RED)
            val l = highlight(line, YELLOW)

            println(s"$f:$i $l")
        }
      } finally {
        bs.close()
      }
    }
  }
}
