import sbtrelease.ReleasePlugin._


name := "scala-storm"

// If you comment this out, SBT 0.10 will default to Scala 2.8.1
scalaVersion := "2.10.4"

organization := "com.mediacrossing"

// sbt defaults to <project>/src/test/{scala,java} unless we put this in
unmanagedSourceDirectories in Test <<= Seq( baseDirectory( _ / "test" ) ).join

unmanagedSourceDirectories in Compile <<= Seq( baseDirectory( _ / "src" ) ).join

resolvers ++= Seq("clojars" at "http://clojars.org/repo/",
                  "clojure-releases" at "http://build.clojure.org/releases")

libraryDependencies += "org.apache.storm" % "storm-core" % "0.9.2-incubating" % "provided"

// This is to prevent error [java.lang.OutOfMemoryError: PermGen space]
javaOptions += "-XX:MaxPermSize=1g"

javaOptions += "-Xmx2g"

scalacOptions ++= Seq("-Yresolve-term-conflict:package", "-unchecked", "-deprecation", "-feature", "-language:implicitConversions", "-language:postfixOps")

// When doing sbt run, fork a separate process.  This is apparently needed by storm.
fork := true

// set Ivy logging to be at the highest level - for debugging
ivyLoggingLevel := UpdateLogging.Full

// Aagin this may be useful for debugging
logLevel := Level.Info

//************ Publishing info *********
publishMavenStyle := true

publishArtifact in Test := false

pomIncludeRepository := { _ => false }

pomExtra := (
  <url>https://github.com/mediacrossinginc/ScalaStorm</url>
  <licenses>
    <license>
      <name>Apache</name>
      <url>http://www.apache.org/licenses/LICENSE-2.0.html</url>
      <distribution>repo</distribution>
    </license>
  </licenses>
  <scm>
    <url>git@github.com:mediacrossinginc/ScalaStorm.git</url>
    <connection>scm:git:git@github.com:mediacrossinginc/ScalaStorm.git</connection>
  </scm>)



releaseSettings ++ Seq(ReleaseKeys.versionBump := sbtrelease.Version.Bump.Minor)
