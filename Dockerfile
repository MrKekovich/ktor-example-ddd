# Use a base image with JDK for building the project
FROM gradle:7.4.2-jdk17 as build

# Set the working directory
WORKDIR /app

# Copy the project files to the working directory, excluding those specified in .dockerignore
COPY . .

# Run the gradle build command
RUN ./gradlew build

# Use a lightweight JRE image for running the app
FROM amazoncorretto:17

# Set the working directory
WORKDIR /app

# Copy the built jar file from the build stage
COPY --from=build /app/build/libs/*.jar app.jar

# Expose the application port
EXPOSE 8080

# Run the application
CMD ["java", "-jar", "app.jar"]
