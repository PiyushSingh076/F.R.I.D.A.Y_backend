# Use Maven with OpenJDK 23 to build the app
FROM maven:openjdk-23-jdk as build

# Set the working directory in the container
WORKDIR /app

# Copy the project files into the container
COPY . .

# Run Maven to build the app (skip tests for faster build)
RUN mvn clean package -DskipTests

# Use OpenJDK 23 slim image to run the app
FROM openjdk:23-jdk-slim

# Set the working directory in the container
WORKDIR /app

# Copy the built JAR file from the build stage
COPY --from=build /app/target/demo-0.0.1-SNAPSHOT.jar /app/demo.jar

# Expose the port the app runs on (default for Spring Boot is 8080)
EXPOSE 8080

# Start the Spring Boot app using the JAR file
ENTRYPOINT ["java", "-jar", "demo.jar"]
