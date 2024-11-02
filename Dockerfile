# Use an OpenJDK image as the base
FROM openjdk:17-jdk-slim

# Set the working directory in the container
WORKDIR /Kafka_WS_Demo

# Copy the JAR file to the container
COPY target/Kafka_WS_Demo-0.0.1-SNAPSHOT.jar Kafka_WS_Demo-0.0.1-SNAPSHOT.jar

# Expose the application port
EXPOSE 8080

# Run the Spring Boot application
ENTRYPOINT ["java", "-jar", "Kafka_WS_Demo-0.0.1-SNAPSHOT.jar"]
