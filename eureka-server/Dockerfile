# Use a lightweight official OpenJDK image as the base.
FROM openjdk:17-jdk-slim

# The JAR file is expected to be built locally and copied into the image.
# The name of the JAR file should be adjusted to match your project's output.
ARG JAR_FILE=target/eureka-server-0.0.1-SNAPSHOT.jar

# Copy the JAR file from the host to the container.
COPY ${JAR_FILE} app.jar

# Define the command to run the application when the container starts.
ENTRYPOINT ["java", "-jar", "/app.jar"]