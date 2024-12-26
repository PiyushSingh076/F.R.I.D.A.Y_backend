FROM ubuntu:latest AS build
RUN apt-get-update
RUN apt-get install openjdk-23-jdk -y
COPY . .
RUN ./gradlew bootJar --no-daemon

FROM openjdk:17-jdk-slim
EXPOSE 8080
COPY --from=build /app/target/SpringAiDemo-0.0.1-SNAPSHOT.jar /app/SpringAiDemo.jar
CMD ["java", "-jar", "SpringAiDemo.jar"]