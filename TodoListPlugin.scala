package com.github.fedragon

import sbt._
import Keys._

object TodoListPlugin extends AutoPlugin {
  object autoImport {
    val todolist = taskKey[Seq[File]]("Look for TODOs in source files.")
    val todolistTags = settingKey[Seq[String]]("Tags to look for.")

    lazy val baseTodoListSettings: Seq[Def.Setting[_]] = Seq(
      todolist := {
        TodoList((sources in Compile).value ++ (sources in Test).value, (todolistTags in todolist).value)
      },
      todolistTags in todolist := Seq("TODO", "FIXME")
    )
  }

  import autoImport._
  override def requires = sbt.plugins.JvmPlugin
  override def trigger = allRequirements
  override val projectSettings =
    inConfig(Compile)(baseTodoListSettings) ++
    inConfig(Test)(baseTodoListSettings)
}

object TodoList {
  import scala.io.Source

  def apply(sources: Seq[File], tags: Seq[String]): Seq[File] = {
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
    sources
  }
}
