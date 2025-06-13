# Build stage
FROM maven:3.8.4-openjdk-17-slim AS build
WORKDIR /app

# Copy Maven wrapper files first
COPY mvnw .
COPY mvnw.cmd .
COPY .mvn .mvn

# Set execute permission for mvnw
RUN chmod +x mvnw

# Copy project files
COPY pom.xml .
COPY src/ src/

# Build the application
RUN ./mvnw clean package -DskipTests

# Run stage
FROM eclipse-temurin:17-jdk-alpine
WORKDIR /app
VOLUME /tmp

# Copy only the built jar
COPY --from=build /app/target/*.jar app.jar

# Set Spring profile to use external configuration
ENV SPRING_PROFILES_ACTIVE=prod

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "app.jar"]