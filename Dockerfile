 # Fase 1: Construcción con Maven (Builder)
FROM eclipse-temurin:17-jdk AS builder
WORKDIR /app

# Copiar archivos esenciales para optimizar el caché
COPY Backend_NB/pom.xml Backend_NB/mvnw ./
COPY Backend_NB/.mvn/ .mvn/
RUN chmod +x Backend_NB/mvnw && Backend_NB/mvnw dependency:go-offline

# Copiar el código fuente y compilar
COPY Backend_NB/src/ Backend_NB/src/
RUN Backend_NB/mvnw clean package -DskipTests

# Fase 2: Imagen final segura y optimizada
FROM gcr.io/distroless/java17-debian11
WORKDIR /app

# Crear un usuario seguro para evitar ejecutar como root
RUN addgroup --system spring && adduser --system --group spring
USER spring:spring

# Copiar el JAR desde la fase de construcción
COPY --from=builder /app/Backend_NB/target/*.jar app.jar

# Exponer solo el puerto necesario (8080 por defecto en Spring Boot)
EXPOSE 8080

# Configuración de logs
ENV LOGGING_LEVEL_ROOT=INFO
ENV LOGGING_FILE_PATH=/app/logs/app.log

# Comando de inicio de la aplicación
ENTRYPOINT ["java", "-jar", "app.jar"]
