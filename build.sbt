
scalaVersion := "2.12.4"

enablePlugins(JmhPlugin)

lazy val firrtl = project in file("firrtl")
lazy val bench = (project in file(".")).dependsOn(firrtl)


