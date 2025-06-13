# Build stage
FROM maven:3.8.4-openjdk-17-slim AS build
WORKDIR /app

# Copy only pom.xml and mvnw files first to leverage Docker layer caching
COPY pom.xml .
COPY .mvn/ .mvn/
COPY mvnw mvnw
COPY mvnw.cmd mvnw.cmd

# Download dependencies (this layer will be cached if pom.xml doesn't change)
RUN ./mvnw dependency:go-offline

# Now copy the source code
COPY src/ src/

# Package the application
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
