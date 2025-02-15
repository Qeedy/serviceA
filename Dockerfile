# Gunakan JDK untuk build
FROM eclipse-temurin:17-jdk AS build

# Set working directory
WORKDIR /app

# Copy seluruh kode sumber
COPY . .

# Build aplikasi menggunakan Maven
RUN mvn clean package -DskipTests

# Gunakan JDK untuk runtime
FROM eclipse-temurin:17-jre

# Set working directory untuk runtime
WORKDIR /app

# Copy file JAR dari hasil build sebelumnya
COPY --from=build /app/target/*.jar app.jar

# Jalankan aplikasi
CMD ["java", "-Dserver.port=8080", "-jar", "app.jar"]
