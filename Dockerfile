# build
FROM maven:latest as BUILDER
ADD . /build
WORKDIR /build
ENV MAVEN_OPTS="-XX:+TieredCompilation -XX:TieredStopAtLevel=1"
RUN mvn -B -e -T 1C verify

# package without maven
FROM openjdk:11-oracle
EXPOSE 8081
COPY --from=BUILDER /build/target/fdadi-frontend.jar app.jar
ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","/app.jar"]
