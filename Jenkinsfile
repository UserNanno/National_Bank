pipeline {
    agent any
    tools {
        jdk 'JDK17'
        maven 'Maven3'
    }
    environment {
        SCANNER_HOME = tool 'SonarScanner'
    }SonarScanner
    stages {
        stage('Clean Workspace') {
            steps {
                cleanWs()
            }
        }
        
        stage("Git Checkout") {
            steps {
                git branch: 'master',
                changelog: false, 
                poll: false, 
                url: 'https://github.com/UserNanno/National_Bank.git'
            }
        }
        
        stage("Build Backend with Maven") {
            steps {
                dir('Backend_NB') {
                    sh "mvn clean package -DskipTests"
                }
            }
        }

        stage("Unit Tests - Backend") {
            steps {
                dir('Backend_NB') {
                    sh "mvn test"
                }
            }
        }

        stage("Sonarqube Analysis - Backend") {
            steps {
                dir('Backend_NB') {
                    sh "mvn sonar:sonar -Dsonar.projectKey=NationalBank_Backend -Dsonar.projectName=NationalBank_Backend -Dsonar.host.url=http://sonarqube:9000 -Dsonar.token=squ_65db13a9b849a3ad6e22c24cd76ef02e0d806117"
                }
            }
        }

        stage("Build & Push Docker Images") {
            steps {
                script {
                    def backendImage = "usernanno/nationalbank-backend:latest"

                    // Autenticación en Docker Hub con credenciales de Jenkins
                    withCredentials([usernamePassword(credentialsId: 'docker-hub-credentials', usernameVariable: 'DOCKER_USER', passwordVariable: 'DOCKER_PASS')]) {
                        sh "echo $DOCKER_PASS | docker login -u $DOCKER_USER --password-stdin"
                    }

                    // Construcción de la imagen del backend
                    sh "docker build -t ${backendImage} Backend_NB"

                    // Subir la imagen a Docker Hub
                    sh "docker push ${backendImage}"
                }
            }
        }

        stage("Deploy with Docker Compose") {
            steps {
                sh "docker-compose down"
                sh "docker-compose up -d --build"
            }
        }
    }
}
