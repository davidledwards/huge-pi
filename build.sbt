lazy val compilerSettings = Seq(
  scalaVersion := "2.11.8",
  scalacOptions ++= Seq(
    "-target:jvm-1.6",
    "-deprecation",
    "-unchecked",
    "-feature",
    "-encoding", "UTF-8"
  )
)

lazy val rootProject = (project in file(".")).
  settings(
    name := "huge-pi",
    organization := "com.loopfor.pi",
    version := "0.1",
    homepage := Some(url("https://github.com/davidledwards/huge-pi")),
    licenses := Seq("Apache License, Version 2.0" -> url("http://www.apache.org/licenses/LICENSE-2.0.txt")),
    scmInfo := Some(ScmInfo(
      url("https://github.com/davidledwards/huge-pi"),
      "scm:git:https://github.com/davidledwards/huge-pi.git",
      Some("scm:git:https://github.com/davidledwards/huge-pi.git")
    ))
  ).
  settings(compilerSettings: _*)
