
scalaVersion := "2.11.7"

enablePlugins(JmhPlugin)

lazy val firrtl = project in file("firrtl")
lazy val bench = (project in file(".")).dependsOn(firrtl)


