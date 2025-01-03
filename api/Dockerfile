FROM gradle:8.11.1-jdk17-alpine AS builder
WORKDIR /app

COPY src /app/src
COPY build.gradle /app
COPY settings.gradle /app

ENV GRADLE_USER_HOME=/tmp/gradle
RUN mkdir -p /tmp/gradle && chmod -r /tmp/gradle
RUN gradle clean build --no-daemon --stacktrace

FROM eclipse-temurin:17-jre-alpine
WORKDIR /app
COPY --from=builder /app/build/libs/*.jar app.jar

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "/app/app.jar"]
