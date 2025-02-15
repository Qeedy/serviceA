# Gunakan image JDK 17 untuk menjalankan aplikasi
FROM eclipse-temurin:17-jdk

# Set working directory dalam container
WORKDIR /app

# Copy file JAR dari hasil build Maven ke dalam container
COPY target/*.jar app.jar

# Jalankan aplikasi Spring Boot
CMD
