# sbt-todolist

Finds _work in progress_ tags (`TODO`, `FIXME`, ...) in source files and prints them to the console.

## Installation

Add the following line to your `plugins.sbt` file:

    addSbtPlugin("com.github.fedragon" % "sbt-todolist" % "0.1")

## Usage

    sbt todolist
    // prints out all occurrences of configured tasks in source files

    sbt todolistTags
    // shows the configured tags

## Customize tags

Add the following line to your `build.sbt` file:

    todolistTags := Set("<a tag>", "<another tag>")
