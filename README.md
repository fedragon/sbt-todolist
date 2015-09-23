# sbt-todolist

Finds _work in progress_ tags (`TODO`, `FIXME`, ...) in source files and prints them to the console.

## Installation

Add the following line to your local (`project/plugins.sbt`) or global (`~/.sbt/0.13/plugins/plugins.sbt`) configuration:

    resolvers ++= Seq(
        "Sonatype OSS Releases" at "https://oss.sonatype.org/content/repositories/releases/"
    )

    addSbtPlugin("com.github.fedragon" % "sbt-todolist" % "0.4")

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

The tags to look for can be redefined by adding the following line to your `build.sbt`:

    todosTags := Set("<a tag>", "<another tag>")
