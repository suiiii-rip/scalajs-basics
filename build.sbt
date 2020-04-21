
val versions = new {

  val scala = "2.13.1"
}

val dependencies = {
  import versions._
  new {
//    val `kubernetes-client` = "io.kubernetes" % "client-java" % versions.`kubernetes-client`
//    val scalaJsDom = "org.scala-js" %%% "scalajs-dom" % "1.0.0"
  }
}

val commonSettings = Seq(
  organization := "com.myapp",
  version := "1.0.0-SNAPSHOT",
  scalaVersion := versions.scala,
  scalacOptions := Seq("-unchecked", "-deprecation", "-feature", "-encoding", "utf8"),
  dependencyOverrides ++= {
    import dependencies._
    Seq(

    )
  },
)

lazy val core = Project(
  id = "myapp-core",
  base = file("core")
)
  .settings(
    libraryDependencies ++= {
      import dependencies._
      Seq(
      )
    },
    unusedCompileDependenciesFilter -= moduleFilter("ch.qos.logback", "logback-classic"),
  )
  .settings(commonSettings: _*)
  .enablePlugins(ReproducibleBuildsPlugin)


lazy val app = Project(
  id = "myapp-app",
  base = file("app")
)
  .settings(
    libraryDependencies ++= {
      import dependencies._
      Seq(
        "org.scala-js" %%% "scalajs-dom" % "1.0.0",
        "com.lihaoyi" %%% "utest" % "0.7.4" % "test"
      )
    },
    crossPaths := false,
    testFrameworks += new TestFramework("utest.runner.Framework"),

  )
  .settings(commonSettings: _*)
  .settings(
    scalaJSUseMainModuleInitializer := true,
    jsEnv := new org.scalajs.jsenv.jsdomnodejs.JSDOMNodeJSEnv()
  )
  .enablePlugins(ScalaJSPlugin)
  .dependsOn(core)

lazy val myapp = (project in file("."))
  .settings(commonSettings: _*)
  .aggregate(core, app)
