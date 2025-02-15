# Gunakan image Maven dengan JDK untuk build
FROM maven:3.8.6-eclipse-temurin-17 AS build

# Set working directory
WORKDIR /app

# Copy pom.xml dan unduh dependencies (agar cache bisa digunakan)
COPY pom.xml .
RUN mvn dependency:go-offline

# Copy seluruh kode sumber
COPY src/ src/

# Build aplikasi
RUN mvn clean package -DskipTests

# Gunakan JRE untuk runtime (lebih ringan)
FROM eclipse-temurin:17-jre

# Set working directory untuk runtime
WORKDIR /app

# Copy file JAR dari hasil build sebelumnya
COPY --from=build /app/target/*.jar app.jar

# Jalankan aplikasi
CMD ["java", "-Dserver.port=8080", "-jar", "app.jar"]
