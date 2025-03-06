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


## ⚙️Principales servicios WEB
![image](https://github.com/user-attachments/assets/e7782bb2-c159-4e8f-9b73-2c4abf46ff35)
![image](https://github.com/user-attachments/assets/ef931cd9-cbc6-4640-857f-b0b378587771)

#### API CUENTAS BANCARIAS // /api/bank-accounts/{id}
![image](https://github.com/user-attachments/assets/82e6a589-14ec-4ef6-adae-e4f64cbf22f0)

#### API PAGO SERVICIOS // /api/service-payments/update/{id}
![image](https://github.com/user-attachments/assets/c5dc532a-aa7f-4d91-8db2-5346fe0698b7)




