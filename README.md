## 🚀 Proyecto NationalBank con Jenkins 

# 📋 Integrantes

- Lizar Estrada, Adrián Jesús (Jefe de Proyecto)
- Cóndor Marín, Jesús Ernesto
- Canecillas Contreras, Juan Mariano
- Manco Méndez, Elvis Neiser
- Vásquez Gonzales Sebastián
- Huarhua Piñas, Edson Sebastian

## ⚙️Propósito del proyecto
La pandemia de COVID-19 ha acelerado la adopción de servicios bancarios digitales en todo el mundo, incluyendo Perú. Esta tendencia ha aumentado significativamente la demanda de servicios bancarios en línea y móviles, pero la plataforma actual del Banco de la Nación del Perú (BN) enfrenta varios obstáculos para satisfacer estas demandas crecientes:
Interoperabilidad Limitada: La dificultad para integrarse con otros sistemas y plataformas dificulta el acceso fluido a cuentas, la realización de transferencias interbancarias y la conexión con servicios de terceros, como billeteras digitales.
Lentitud e inestabilidad: Los clientes experimentan frustración debido a la lentitud en la carga de páginas, la ejecución de transacciones y las fallas del sistema, especialmente durante períodos de alta demanda. Esto se traduce en tiempos de espera prolongados e interrupciones en los procesos.
Ineficiencia Operativa: Procesos manuales y lentos, como la apertura de cuentas, la solicitud de créditos y la atención al cliente, aumentan los costos operativos, reducen la productividad y generan retrasos en la prestación de servicios.
Falta de Escalabilidad: La arquitectura de la plataforma actual no está diseñada para soportar el crecimiento proyectado del banco, la expansión de servicios digitales y el aumento en el volumen de transacciones.
Vulnerabilidades de Seguridad: La seguridad de los datos y las transacciones se ve comprometida por vulnerabilidades que podrían ser explotadas por ciberdelincuentes, como ataques de phishing, malware y robo de identidad.


## ⚙️Visión General de la arquitectura del proyecto
Este proyecto está diseñado siguiendo los principios de Domain-Driven Design (DDD) y Arquitectura Limpia, lo que permite una estructura modular, mantenible y escalable.

🏗 Principios Claves de la Arquitectura
Separación de Responsabilidades: Cada capa tiene una función específica, reduciendo el acoplamiento y facilitando la escalabilidad.
Modelo de Dominio Centrado: La lógica de negocio se organiza alrededor de entidades y agregados bien definidos.
Independencia de Infraestructura: La lógica de negocio no depende de frameworks o bases de datos específicas.
Testabilidad: Gracias a la separación de capas, se facilita la implementación de pruebas unitarias y de integración.

![image](https://github.com/user-attachments/assets/5b05f396-97e7-466c-a61c-d6ed5854f908)

#Servicios de dominio
![image](https://github.com/user-attachments/assets/9444a95d-8549-48b9-b04a-74963c7ef778)

#Organización de servicios
![image](https://github.com/user-attachments/assets/bcc712c3-1c8d-4f02-a8d7-ca565d1d17b6)


## ⚙️Preparación del entorno

Clonamos el repositorio mediante el siguiente comando:

`git clone https://github.com/JesusCondor16/VerificacionYValidacion.git`

> [!IMPORTANT]
> Debes tener instalado git en tu sistema

Prepararemos el entorno mediante la instalación de las librerías para el backend y frontend.


Ejecutamos `mvn install` en el directorio del backend y también del frontend para instalar las librerías necesarias.

**Frontend**

```sh
cd frontend_nb
npm install
```

**Backend**

```sh
cd Backend_NB
mvn install
```

## 🔨Construcción Automática

Para la construcción del proyecto utilizaremos el empaquetador [esbuild🪧](https://esbuild.github.io/).

#### Construcción del Backend



Ejecutamos el comando `mvn clean package -DskipTests` para ejecutar la construcción. En la terminal observaremos un output similar al siguiente:

```
## Pruebas Unitarias
Las pruebas unitarias implementadas a la aplicación para verificar el correcto funcionamiento de las rutas y controladores.

### Tecnologías Utilizadas
+ xUnit: Framework para pruebas de Java.


```
### Ejecución de las pruebas unitarias y funcionales:
### Stage del pipeline en Jenkins:
```
        stage("Unit Tests - Backend") {
            steps {
                dir('Backend_NB') {
                    sh "mvn test"
                }
            }
        }
```

## Analisis de SonarQube

SonarQube es una herramienta que nos ayuda a poder analizar el código fuente e identificar problemas relacionados a seguridad y/o buenas practicas de codificación.


### Objetivo

Garantizar que:

+ Calidad de codigo: En lo que respecta fiabilidad, mantenibilidad y seguridad.

+ Conformidad con estándares.

+ Prevención de problemas a largo plazo .

### Instalación

* Java instalado: Se puede usar desde el Java version 17. Para comprobar si java esta instalado se puede usar el comando "java -version" en una terminal.

* Google Chrome: Instalado y actualizado.

* Sonarqube: Se debe descargar e instalar en la computadora. 

## Pipeline

	stage("Sonarqube Analysis - Backend") {
	    steps {
	        dir('Backend_NB') {
	            withCredentials([string(credentialsId: 'sonar-token', variable: 'SONAR_TOKEN')]) {
	                sh "mvn sonar:sonar -Dsonar.projectKey=NationalBank_Backend -Dsonar.projectName=NationalBank_Backend -Dsonar.host.url=http://sonarqube:9000 -Dsonar.token=$SONAR_TOKEN"

	            }
	        }
	    }
	}


## ⚙️Documentación de Principales servicios WEB

Este proyecto cuenta con una documentación detallada de los servicios web utilizando OpenAPI 3.0 y Swagger, permitiendo una mejor comprensión e interacción con la API.

📖 Documentación de la API
La documentación generada con Swagger proporciona una interfaz interactiva donde los desarrolladores pueden explorar los endpoints, probar solicitudes y visualizar respuestas en tiempo real.

🔹 Acceso a la documentación
Puedes acceder a la documentación desde el entorno local en la siguiente URL:

http://localhost:8080/swagger-ui/index.html#

🔹 Características de la documentación:

Descripción detallada de los endpoints, parámetros y respuestas.
Posibilidad de probar los servicios directamente desde la interfaz.
Especificación basada en OpenAPI 3.0 para garantizar compatibilidad con otras herramientas.
📌 Cómo generar la documentación
Si necesitas actualizar o regenerar la documentación, sigue estos pasos:

Asegúrate de que todas las rutas y esquemas estén bien documentados en los controladores y modelos.
Ejecuta el servidor con Swagger habilitado.
Accede a la interfaz de Swagger en la URL proporcionada.
Para más detalles, revisa la configuración en el archivo correspondiente dentro del proyecto.

![image](https://github.com/user-attachments/assets/e7782bb2-c159-4e8f-9b73-2c4abf46ff35)
![image](https://github.com/user-attachments/assets/ef931cd9-cbc6-4640-857f-b0b378587771)

#### API CUENTAS BANCARIAS // /api/bank-accounts/{id}
![image](https://github.com/user-attachments/assets/82e6a589-14ec-4ef6-adae-e4f64cbf22f0)

#### API PAGO SERVICIOS // /api/service-payments/update/{id}
![image](https://github.com/user-attachments/assets/c5dc532a-aa7f-4d91-8db2-5346fe0698b7)

## ⚙️Gestión de proyecto
Para la planificación y seguimiento del desarrollo, utilizamos Trello como herramienta principal de gestión de tareas. Nuestro tablero está estructurado en las siguientes columnas:

Usuario 🧑‍💻: Contiene requerimientos, solicitudes y necesidades del usuario final.
Sistema 🏗️: Agrupa tareas relacionadas con la arquitectura, configuración y mejoras del sistema.
Backlog 📋: Almacena todas las tareas pendientes de priorización y desarrollo.
Cada tarea sigue un flujo de trabajo estructurado, asegurando que los cambios sean bien documentados y alineados con los objetivos del proyecto.

✅ Objetivos de la gestión en Trello:

Mejorar la visibilidad del progreso del proyecto.
Mantener un registro claro de cambios y decisiones.
Optimizar la comunicación entre los miembros del equipo.

![image](https://github.com/user-attachments/assets/a6a24e2f-d9de-4892-ae31-49eabc42fa7f)


