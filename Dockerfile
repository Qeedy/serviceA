# Gunakan JDK 22 untuk build dan runtime
FROM eclipse-temurin:22-jdk AS build

# Set working directory
WORKDIR /app

# Copy pom.xml dan install dependencies (agar cache bisa digunakan)
COPY pom.xml .
RUN mvn dependency:go-offline

# Copy seluruh kode sumber
COPY src/ src/

# Build aplikasi
RUN mvn clean package -DskipTests

# Gunakan JDK 22 untuk runtime (pastikan sama)
FROM eclipse-temurin:22-jre

# Set working directory untuk runtime
WORKDIR /app

# Copy file JAR dari hasil build sebelumnya
COPY --from=build /app/target/*.jar app.jar

# Jalankan aplikasi
CMD ["java", "-Dserver.port=8080", "-jar", "app.jar"]
