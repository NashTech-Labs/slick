name := "slickdemo"

scalaVersion := "2.10.2"

organization := "Knoldus"

libraryDependencies ++= List(
        "com.typesafe.slick" %% "slick" % "1.0.1",
        "org.slf4j" % "slf4j-api" % "1.6.4",
        "postgresql" %  "postgresql" % "9.1-901.jdbc4"
    )
    
