FROM eclipse-temurin:17-jdk-alpine
LABEL authors="igorcrocha"
VOLUME /tmp
COPY target/*.jar app.jar
ENTRYPOINT ["java", "-jar", "/app.jar"]