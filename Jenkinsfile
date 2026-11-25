pipeline {
    agent any

    tools {
        maven 'Maven3'
    }

    triggers {
        pollSCM('H/5 * * * *')
    }

    stages {
        stage('Test') {
            steps {
                bat 'mvn clean test -Dheadless=true -Dmaven.test.failure.ignore=true'
            }
        }
    }

    post {
        always {
            junit allowEmptyResults: true, testResults: 'target/surefire-reports/*.xml'
            archiveArtifacts artifacts: 'target/allure-results/**', allowEmptyArchive: true
        }
    }
}