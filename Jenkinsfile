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
                dir('Backend_NB') { // Cambiamos al directorio correcto
                    bat "\"%MAVEN_HOME%\\bin\\mvn\" clean package"
                }
            }
        }

        stage('SonarQube Analysis') {
            steps {
                dir('Backend_NB') { 
                    withSonarQubeEnv('sonarqube') {
                        bat "\"%MAVEN_HOME%\\bin\\mvn\" sonar:sonar"
                    }
                }
            }
        }

        stage('Unit Tests') {
            steps {
                dir('Backend_NB') { 
                    bat "\"%MAVEN_HOME%\\bin\\mvn\" test"
                }
            }
            post {
                always {
                    junit 'Backend_NB/target/surefire-reports/*.xml'
                }
            }
        }

        stage('API Tests with Postman') {
            steps {
                bat 'newman run pruebas-apis.postman_collection.json --reporters junit'
            }
            post {
                always {
                    junit 'newman/*.xml'
                }
            }
        }

        stage('Deploy and Restart') {
            steps {
                echo 'Desplegando aplicación...'
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