FROM eclipse-temurin:17-jdk-jammy
VOLUME /tmp
ARG JAR_FILE
COPY ./target/*.jar /demo.jar
ENV JAVA_OPTS=""
ENTRYPOINT ["java", "-jar", "/demo.jar"]
CMD [""]