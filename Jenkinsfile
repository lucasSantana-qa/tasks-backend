pipeline {
    agent any
    stages {
        stage('Unit tests Backend') {
            steps {
                bat 'mvn clean verify'
            }
        }
        stage('Sonar Analysis') {
            environment {
                scannerHome = tool 'SONAR_SCANNER'
            }
            steps {
                withSonarQubeEnv('SONAR_LOCAL') {
                    bat "${scannerHome}/bin/sonar-scanner -e -Dsonar.projectKey=DeployBack -Dsonar.host.url=http://localhost:9000 -Dsonar.java.binaries=target -Dsonar.coverage.jacoco.xmlReportPaths=target/site/jacoco/*.xml -Dsonar.coverage.exclusions=**/.mvn/**,**/src/test/**,**/model/**,**/Application.java"
                }
            }
        }
        stage('Quality Gate') {
            steps {
                sleep(15)
                timeout(time:1, unit: 'MINUTES') {
                    waitForQualityGate abortPipeline: true
                }
            }
        }
        stage('Deploy Backend') {
            steps {
                deploy adapters: [tomcat9(alternativeDeploymentContext: '', credentialsId: 'TOMCAT_CREDENTIALS', path: '', url: 'http://localhost:8001/')], contextPath: 'tasks-backend', onFailure: false, war: 'target/tasks-backend.war'
            }
        }
        stage('API tests') {
            steps {
                dir('api-test') {
                    git 'https://github.com/lucasSantana-qa/tasks-api-tests'            
                    bat 'mvn test'
                }
            }
        }
        stage('Deploy Frontend') {
            steps {
                dir('tasks-frontend') {
                    git 'https://github.com/lucasSantana-qa/tasks-frontend'            
                    bat 'mvn clean package'
                    deploy adapters: [tomcat9(alternativeDeploymentContext: '', credentialsId: 'TOMCAT_CREDENTIALS', path: '', url: 'http://localhost:8001/')], contextPath: 'tasks', onFailure: false, war: 'target/tasks.war'            
                }
            }
        }
        stage('Functional testes Frontend') {
            dir('functional-test') {
                git https://github.com/lucasSantana-qa/tasks-functional-tests
                bat 'mvn test'
            }
        }
    }
}

