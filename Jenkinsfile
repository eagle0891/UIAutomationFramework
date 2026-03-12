pipeline {
    agent any

    parameters {
        choice(name: 'ENVIRONMENT', choices: ['qa', 'dev', 'staging'], description: 'Select Environment')
        string(name: 'BROWSER', defaultValue: 'chrome', description: 'Browser to run tests')
        booleanParam(name: 'HEADLESS', defaultValue: true, description: 'Run in headless mode?')
    }

    stages {
        stage('Cleanup & Checkout') {
            steps {
                cleanWs()
                checkout scm
            }
        }

        stage('API Health Checks') {
            steps {
                echo "Running API Layer Tests for ${params.ENVIRONMENT}..."
                sh "mvn clean test -Dcucumber.filter.tags='@api' -Denv=${params.ENVIRONMENT}"
            }
        }

        stage('UI Regression') {
            steps {
                echo "API Passed. Running UI Layer for ${params.ENVIRONMENT} on ${params.BROWSER}..."
                sh "mvn test -Dcucumber.filter.tags='@ui' -Denv=${params.ENVIRONMENT} -Dbrowser=${params.BROWSER} -Dheadless=${params.HEADLESS}"
            }
        }
    } // End of Stages

    post {
        always {
            // Add 'allowMissing: true' temporarily to see if it's a path issue
            publishHTML(target: [
                allowMissing: true,
                alwaysLinkToLastBuild: true,
                keepAll: true,
                reportDir: 'target/ExtentReport',
                reportFiles: 'SparkReport.html',
                reportName: 'Automation Test Report'
            ])

            // This is the magic line for the graph
            junit 'target/surefire-reports/*.xml'
        }
        failure {
            echo "Tests failed! Sending notification..."
        }
    }
} // End of Pipeline