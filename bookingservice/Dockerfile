FROM maven:eclipse-temurin AS builder

WORKDIR /app

COPY . .

RUN mvn clean install

FROM openjdk:25-slim-bullseye

WORKDIR /app

COPY --from=builder /app/target/*.jar app.jar

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "app.jar"]