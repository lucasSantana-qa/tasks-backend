pipeline {
    agent any
    stages {
        stage('Build backend') {
            steps {
                bat 'mvn clean verify -DskipTests=true'
            }
        }
        stage('Unit tests') {
            steps{
                bat 'mvn test'
            }
        }
        stage('Sonar Analysis') {
            environment {
                scannerHome = tool 'SONAR_SCANNER'
            }
            steps{
                withSonarQubeEnv('SONAR_LOCAL') {
                    bat "${scannerHome}/bin/sonar-scanner -e -Dsonar.projectKey=DeployBack -Dsonar.host.url=http://localhost:9000 -Dsonar.java.binaries=target -Dsonar.coverage.jacoco.xmlReportPaths=target/site/jacoco/*.xml -Dsonar.coverage.exclusions=**/.mvn/**,**/src/test/**,**/model/**,**/Application.java"
                }
            }
        }
    }
}

