# Fase 1: Construcción con Maven y JDK 17
FROM maven:3.8.8-eclipse-temurin-17 AS builder
WORKDIR /app

# Copiar pom.xml y descargar dependencias para optimizar la caché de Docker
COPY pom.xml .
RUN mvn dependency:go-offline

# Copiar el código fuente y compilar la aplicación
COPY src/ src/
RUN mvn clean package -DskipTests

# Fase 2: Imagen final con JDK optimizada
FROM eclipse-temurin:17-jdk AS runtime
WORKDIR /app

# Instalar mysql-client permanentemente
USER root
RUN apt update && apt install -y mysql-client

# Crear un usuario seguro para evitar ejecutar como root
RUN groupadd -r spring && useradd -r -g spring spring
USER spring

# Copiar el JAR desde la fase de construcción
COPY --from=builder /app/target/*.jar app.jar

# Exponer el puerto necesario (8080 por defecto en Spring Boot)
EXPOSE 8080

# Configuración de logs y variables de entorno
ENV LOGGING_LEVEL_ROOT=INFO
ENV SPRING_PROFILES_ACTIVE=default
VOLUME /app/logs

# Comando de inicio de la aplicación
ENTRYPOINT ["java", "-jar", "app.jar"]
