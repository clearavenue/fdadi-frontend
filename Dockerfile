FROM maven:3.6.2-jdk-11 as BUILDER
ADD . /build
WORKDIR /build
RUN mvn clean package

FROM openjdk:11-oracle
EXPOSE 8081
ADD /target/*.jar app.jar
ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","/app.jar"]