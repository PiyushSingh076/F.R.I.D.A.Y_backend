# Use the Maven image as base
FROM maven:3.8.4-openjdk-11 as build

# Install OpenJDK 23 (using apt-get for example)
RUN apt-get update && apt-get install -y openjdk-23-jdk

# Set the working directory
WORKDIR /app

# Copy project files into the container
COPY . .

# Build the application (skip tests for faster builds)
RUN mvn clean package -DskipTests

# Use OpenJDK 23 slim image for running the application
FROM openjdk:23-jdk-slim

# Set working directory for the runtime stage
WORKDIR /app

# Copy the jar file from the build stage
COPY --from=build /app/target/demo-0.0.1-SNAPSHOT.jar /app/demo.jar

# Expose the required port
EXPOSE 8080

# Command to run the Spring Boot application
ENTRYPOINT ["java", "-jar", "demo.jar"]
