pipeline {
    agent any

    environment {
        SONARQUBE_SERVER = 'sonarqube' // Nombre del servidor SonarQube en Jenkins
        MAVEN_HOME = tool 'Maven 3.9.9' // Nombre de la herramienta configurada en Jenkins
    }

    stages {
        stage('Git Checkout') {
            steps {
                git 'https://github.com/UserNanno/National_Bank.git'
            }
        }

        stage('Build with Maven') {
            steps {
                sh "${MAVEN_HOME}/bin/mvn clean package"
            }
        }

        stage('SonarQube Analysis') {
            steps {
                withSonarQubeEnv('sonarqube') {
                    sh "${MAVEN_HOME}/bin/mvn sonar:sonar"
                }
            }
        }

        stage('Unit Tests') {
            steps {
                sh "${MAVEN_HOME}/bin/mvn test"
            }
            post {
                always {
                    junit '**/target/surefire-reports/*.xml' // Recopilar reportes de JUnit
                }
            }
        }

        stage('API Tests with Postman') {
            steps {
                sh 'newman run pruebas-apis.postman_collection.json --reporters junit'
            }
            post {
                always {
                    junit 'newman/*.xml' // Recopilar reportes de Newman (Postman CLI)
                }
            }
        }

        stage('Deploy and Restart') {
            steps {
                echo 'Desplegando aplicación...'
                // Aquí puedes agregar pasos para desplegar tu aplicación
            }
        }
    }

    post {
        success {
            echo '¡Pipeline ejecutado correctamente! 🎉'
        }
        failure {
            echo 'Hubo un error en el Pipeline ❌'
        }
    }
}
