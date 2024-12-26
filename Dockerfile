# Use official Maven image to build the app
FROM maven:3.8.4-openjdk-11 as build

# Set the working directory
WORKDIR /app

# Copy the project files into the container
COPY . .

# Build the app (skip tests for faster build)
RUN mvn clean install -DskipTests

# Use the official OpenJDK 11 image to run the app
FROM openjdk:11-jre-slim

# Set the working directory
WORKDIR /app

# Copy the JAR file from the build stage
COPY --from=build /app/target/SpringAiDemo-0.0.1-SNAPSHOT.jar /app/SpringAiDemo.jar

# Expose port (default for Spring Boot is 8080)
EXPOSE 8080

# Run the Spring Boot application
CMD ["java", "-jar", "SpringAiDemo.jar"]
