# Base image with Maven and Java 17
FROM maven:3.9-eclipse-temurin-17

# Working folder inside the image
WORKDIR /app

# Copy pom.xml first and download dependencies (cached layer)
COPY pom.xml .
RUN mvn -q dependency:go-offline

# Copy the test code (changes often, so it comes last)
COPY src ./src

# Default command when the container starts
CMD ["mvn", "test", "-Dgrid.url=http://selenium-hub:4444"]

