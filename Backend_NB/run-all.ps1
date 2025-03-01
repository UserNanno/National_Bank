Write-Host "⏳ Apagando contenedores anteriores..."
docker-compose down

Write-Host "🚀 Levantando servicios con Docker Compose..."
docker-compose up -d
Start-Sleep -Seconds 5   # Espera 5 segundos antes de ejecutar el siguiente comando


Write-Host "🛠️ Ejecutando pruebas y análisis con SonarQube..."
docker run --rm --network=nationalbank-network -v ${PWD}:/app -w /app eclipse-temurin:17-jdk bash -c "apt update && apt install -y maven && mvn clean verify sonar:sonar"

