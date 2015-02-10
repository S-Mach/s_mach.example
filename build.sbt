scalaVersion := "2.11.1"

organization := "net.s_mach"

name := "example"

version := "0.0.1"

scalacOptions ++= Seq("-feature","-unchecked", "-deprecation")

libraryDependencies += "org.scalatest" % "scalatest_2.11" % "2.2.0" % "test"
