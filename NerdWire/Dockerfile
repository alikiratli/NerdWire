# Build stage
FROM maven:3.8.4-openjdk-17-slim AS build
WORKDIR /app

# Copy Maven wrapper and POM files first (for better layer caching)
COPY mvnw mvnw.cmd ./
COPY .mvn/ .mvn/
COPY pom.xml .

# Set execute permission for mvnw and download dependencies
RUN chmod +x mvnw && \
    ./mvnw dependency:go-offline

# Copy source files
COPY src/ src/

# Build the application
RUN ./mvnw clean package -DskipTests

# Run stage
FROM eclipse-temurin:17-jdk-alpine
WORKDIR /app

# Add timezone configuration
RUN apk add --no-cache tzdata
ENV TZ=Europe/Istanbul

# Create volume for temporary files
//VOLUME /tmp

# Copy the built jar from build stage
COPY --from=build /app/target/*.jar app.jar

# Set Spring and JVM configurations
ENV SPRING_PROFILES_ACTIVE=prod
ENV JAVA_OPTS="-XX:+UseContainerSupport -XX:MaxRAMPercentage=75.0 -Djava.security.egd=file:/dev/./urandom"

EXPOSE 8080

# Use shell form to expand environment variables
ENTRYPOINT java $JAVA_OPTS -jar app.jar
