FROM eclipse-temurin:17-jdk-jammy
WORKDIR /app
COPY target/*.jar /app/demo.jar
ENTRYPOINT ["java", "$JAVA_OPTS", "-jar", "/app/demo.jar"]
