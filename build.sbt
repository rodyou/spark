name := "nginxMonitor"

version := "1.2"

scalaVersion := "2.10.4"

resolvers += "jboss" at "https://repository.jboss.org/nexus/content/repositories/public/"

resolvers += "Bintray sbt plugin releases" at "http://dl.bintray.com/sbt/sbt-plugin-releases/"

libraryDependencies += "org.apache.spark" % "spark-core_2.10" % "1.3.1" % "provided"

libraryDependencies += "org.apache.hadoop" % "hadoop-client" % "2.6.0" % "provided"

libraryDependencies += "org.apache.hadoop" % "hadoop-annotations" % "2.6.0" % "provided"

libraryDependencies += "org.apache.hadoop" % "hadoop-auth" % "2.6.0" % "provided"

libraryDependencies += "org.apache.hadoop" % "hadoop-common" % "2.6.0" % "provided"

libraryDependencies += "org.apache.hadoop" % "hadoop-hdfs" % "2.6.0" % "provided"

libraryDependencies += "org.apache.hadoop" % "hadoop-yarn-api" % "2.6.0" % "provided"

libraryDependencies += "org.apache.hadoop" % "hadoop-yarn-client" % "2.6.0" % "provided"

libraryDependencies += "org.apache.hadoop" % "hadoop-yarn-server-nodemanager" % "2.6.0" % "provided"

libraryDependencies += "org.apache.spark" % "spark-streaming_2.10" % "1.3.1" % "provided"

libraryDependencies += "org.apache.spark" % "spark-streaming-kafka_2.10" % "1.3.1"

libraryDependencies += "org.apache.kafka" % "kafka_2.10" % "0.8.1.1" % "provided"

libraryDependencies += "redis.clients" % "jedis" % "2.6.2"

libraryDependencies += "mysql" % "mysql-connector-java" % "5.1.16"

libraryDependencies += "org.apache.commons" % "commons-dbcp2" % "2.1"

libraryDependencies += "org.slf4j" % "slf4j-api" % "1.7.12"
    