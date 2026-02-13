# ---------- Build Stage ----------
FROM maven:3.9.6-eclipse-temurin-21 AS build
WORKDIR /app
# Copy only pom.xml first to leverage Docker cache
COPY pom.xml .
# Download dependencies without building yet
RUN mvn dependency:go-offline -B
# Copy source code only, Docker reuses cached dependencies and builds much faster
COPY src ./src
# Build JAR
RUN mvn clean package -DskipTests

# ---------- Runtime Stage ----------
FROM eclipse-temurin:21-jre-jammy
WORKDIR /app
# Copy previously built JAR
COPY --from=build /app/target/test-api-*.jar test-api.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "/app/test-api.jar"]
