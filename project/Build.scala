import sbt._
import sbt.Keys._

object ProjectBuild extends Build {

  lazy val root = Project(
    id = "root",
    base = file("."),
    settings = Project.defaultSettings ++ Seq(
      name := "redis-akka",
      organization := "org.solutionset",
      version := "0.1",
      scalaVersion := "2.9.1",
      resolvers += "Typesafe Releases" at "http://repo.typesafe.com/typesafe/releases",      
      libraryDependencies += "com.typesafe.akka" % "akka-actor" % "2.0",
      libraryDependencies += "com.typesafe.akka" % "akka-remote" % "2.0",
      libraryDependencies += "com.typesafe.akka" % "akka-kernel" % "2.0",
      libraryDependencies += "net.debasishg" %% "redisclient" % "2.4.2"
    )
  )
}
