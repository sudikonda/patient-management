FROM maven:3.9.9-eclipse-temurin-21 AS build

WORKDIR /app
COPY pom.xml ./
RUN mvn dependency:go-offline -B
COPY src ./src

RUN mvn clean package -DskipTests

FROM openjdk:21-jdk AS run

WORKDIR /app
COPY --from=build /app/target/api-gateway-0.0.1-SNAPSHOT.jar ./
EXPOSE 4000
ENTRYPOINT ["java", "-jar", "api-gateway-0.0.1-SNAPSHOT.jar"]