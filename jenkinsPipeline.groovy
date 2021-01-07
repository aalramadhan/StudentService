#!groovy

node {
    def mvnHome

    environment {
        GIT_CREDENTIALS_ID = "88c7d5cb-2501-4731-b2fb-1f0a2cbc24b1"
    }

    stage('Preparation') {
        git changelog: false,
            credentialsId: '$GIT_CREDENTIALS_ID',
            poll: false,
            url: 'https://github.com/aalramadhan/StudentService.git'
        mvnHome = tool 'maven3'
    }

    stage('Test') {
        if (isUnix()) {
            sh "'${mvnHome}/bin/mvn' -Dmaven.test.failure.ignore clean test"
        } else {
            bat(/"${mvnHome}\bin\mvn" -Dmaven.test.failure.ignore clean test/)
        }
    }

    stage('IntegrationTest') {
        if (isUnix()) {
            sh "'${mvnHome}/bin/mvn' -Dmaven.test.failure.ignore clean verify -P integration-test"
        } else {
            bat(/"${mvnHome}\bin\mvn" -Dmaven.test.failure.ignore clean verify -P integration-test/)
        }
    }
}
