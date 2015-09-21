# sbt-todolist

Finds _work in progress_ tags (`TODO`, `FIXME`, ...) in source files and prints them to the console.

## Installation

Add the following line to your `plugins.sbt`:

    addSbtPlugin("com.github.fedragon" % "sbt-todolist" % "0.1")

## Usage

    sbt todolist
    // prints out all occurrences of configured tasks in source files

    sbt todolistTags
    // shows the configured tags

### Execute automatically before another task

If you want it to execute every time you run another task (let's say `compile`), add the following line to your `build.sbt`:

    (compile in Compile) <<= (compile in Compile).dependsOn(todolist)

*Note:* I'm still trying to figure out how to make this the default behaviour... any help/contribution would be much appreciated!

## Customize tags

Add the following line to your `build.sbt`:

    todolistTags := Set("<a tag>", "<another tag>")
