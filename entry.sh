#!/bin/bash
# ./mvnw package -Pprod -DskipTests -q -e
export M2_HOME=/opt/maven
export PATH=/opt/apache-maven-3.8.5/bin:$PATH
export PATH=${M2_HOME}/bin:${PATH}
mvn package -Pprod -DskipTests
cd /app/target/
java -jar mobile-app-ws-0.0.1-SNAPSHOT.jar
