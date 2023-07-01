
# Build stage

#FROM maven:3.8.5-openjdk-17  AS build
#COPY . .
#RUN mvn clean package -DskipTests

#
# Package stage
#
#FROM openjdk:17.0.1-jdk-slim
#COPY --from=build /target/dnick-0.0.1-SNAPSHOT.jar demo.jar
#
## ENV PORT=8080
#EXPOSE 8080
#ENTRYPOINT ["java","-jar","demo.jar"]


#FROM openjdk:11
#EXPOSE 8080
#ADD target/spring-boot-docker.jar spring-boot-docker.jar
#ENTRYPOINT ["java", "-jar", "/spring-boot-docker.jar"]
#

#FROM openjdk:17-oracle
#COPY target/*.jar app.jar
#EXPOSE 8089
#ENTRYPOINT ["java","-jar","app.jar"]

FROM maven:3.8.5-openjdk-11 AS build
COPY . .
RUN mvn clean package -DskipTests

FROM adoptopenjdk:11-jre-hotspot
COPY --from=build /target/dnick-0.0.1-SNAPSHOT.jar demo.jar
EXPOSE 8080
ENTRYPOINT ["java","-jar","demo.jar"]


