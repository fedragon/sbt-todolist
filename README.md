# sbt-todolist

Finds _work in progress_ tags (`TODO`, `FIXME`, ...) in source files and prints them to the console.

## Installation

Add the following line to your `plugins.sbt`:

    addSbtPlugin("com.github.fedragon" % "sbt-todolist" % "0.3")

## Usage

    sbt todolist
    // prints out all occurrences of configured tasks in source and test files

    sbt todolistTags
    // shows the configured tags

### Hooking todolist to an existing task

`todolist` can optionally be added as a dependency of another task (`compile`, `test`, ...) so that it will be executed whenever the other task is executed.

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
    // the task `compile` in the `Compile` configuration

and

    withTodolistSettings(test, Test)

## Customization

The tags to look for can be redefined by adding the following line to your `build.sbt`:

    todolistTags := Set("<a tag>", "<another tag>")
