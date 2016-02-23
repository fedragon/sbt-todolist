# sbt-todolist

Finds _work in progress_ tags (`TODO`, `FIXME`, ...) in source files and prints them to the console.

By default, the following tags are matched (the search is case insensitive): `TODO`, `FIXME`, `WIP`, `XXX`.

## About

This plugin was created (and still is) as a personal exploration of sbt internals.

While there is another plugin offering similar functionality (https://github.com/johanandren/sbt-taglist), each plugin went its own way in what they let you customize (keep reading to find out all the available configuration options) so you might want to have a look at both.

## Installation

Add the following line to your local (`project/plugins.sbt`) or global (`~/.sbt/0.13/plugins/plugins.sbt`) configuration:

    resolvers ++= Seq(
        "Sonatype OSS Releases" at "https://oss.sonatype.org/content/repositories/releases/"
    )

    addSbtPlugin("com.github.fedragon" % "sbt-todolist" % "0.6")

## Usage

    sbt todos
    // prints out all occurrences of configured tasks in source and test files

    sbt todosTags
    // shows the configured tags

### Hooking todolist to an existing task

`todos` can optionally be added as a dependency of another task (`compile`, `test`, ...) so that it will be executed whenever the other task is executed.

#### How to execute it when compiling source files

Add the following lines to your `build.sbt`:

    import TodoListPlugin._

    compileWithTodolistSettings

#### How to execute it when running tests

Add the following lines to your `build.sbt`:

    import TodoListPlugin._

    testWithTodolistSettings

#### How to hook it to any other task

Add the following lines to your `build.sbt`:

    import TodoListPlugin._

    withTodolistSettings(<your task>, <its configuration>)

The two examples above are just aliases for (respectively):

    withTodolistSettings(compile, Compile)
    // = the `compile` task in the `Compile` configuration

and

    withTodolistSettings(test, Test)

## Customization

### Tags to look for

The tags to look for can be redefined by adding the following line to your `build.sbt`:

    todosTags := Set("<a tag>", "<another tag>")

The plugin performs case insensitive searches so upcasing (or not) a tag does not influence the outcome.

### Highlighting

The highlighting colors can be redefined by setting one (or all) the following in your `build.sbt`:

    import scala.Console._
    todosColorFilename := CYAN
    todosColorLineNumber := RED
    todosColorLine := YELLOW

The colors shown above are the defaults and can be replaced with any valid `scala.Console` color.
