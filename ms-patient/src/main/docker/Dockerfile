####
# This Dockerfile is used in order to build a container that runs the Spring application
#
# Before building the container image run:
#
# ./mvnw package
#
# Then, build the image with:
#
# docker build -f src/main/docker/Dockerfile -t docker.io/j3riie/medilabo-patient:0.0.1 .
#
# Then run the container using:
#
# docker run -i --rm -p 8080:8080 docker.io/j3riie/medilabo-patient:0.0.1
#
# If you want to include the debug port into your docker image
# you will have to expose the debug port (default 5005) like this :  EXPOSE 8080 5005
#
# Then run the container using :
#
# docker run -i --rm -p 8080:8080 -p 5005:5005 -e JAVA_ENABLE_DEBUG="true" docker.io/j3riie/medilabo-patient:0.0.1
###

FROM maven:3.8.3-openjdk-17 AS build
COPY src /home/app/src
COPY pom.xml /home/app
RUN mvn -f /home/app/pom.xml clean package -DskipTests
EXPOSE 8081
ENTRYPOINT ["java","-jar","/home/app/target/patient-service.jar"]