# Stage 1: Build the application
FROM maven:3.9-eclipse-temurin-21 AS builder

WORKDIR /app

# Copy Maven wrapper and pom.xml
COPY .mvn/ .mvn
COPY mvnw mvnw.cmd pom.xml ./

# Make mvnw executable (if needed, depends on git clone permissions)
RUN chmod +x mvnw

# Download dependencies (optional, but good for layer caching)
RUN ./mvnw dependency:go-offline

# Copy source code
COPY src ./src

# Build the application, skip tests
RUN ./mvnw package -DskipTests

# Stage 2: Create the runtime image
FROM eclipse-temurin:21-jre-jammy

WORKDIR /app

# Create a non-root user and group
RUN groupadd -r appgroup && useradd --no-log-init -r -g appgroup appuser

# Copy the JAR from the builder stage
# Ensure the JAR name/pattern matches what's built (e.g., api-gateway-0.0.1-SNAPSHOT.jar)
COPY --from=builder /app/target/api-gateway-*.jar api-gateway.jar

# Set ownership for the app directory
RUN chown -R appuser:appgroup /app
# Ensure the jar itself is readable by appuser (usually covered by chown -R)
# RUN chmod +r api-gateway.jar 

# Switch to the non-root user
USER appuser

# Expose the application port
EXPOSE 8080

# Healthcheck (requires curl in the base image)
HEALTHCHECK --interval=30s --timeout=5s --start-period=15s --retries=3 \
  CMD curl -f http://localhost:8080/actuator/health || exit 1

# Entry point to run the application
ENTRYPOINT ["java", "-jar", "api-gateway.jar"]
