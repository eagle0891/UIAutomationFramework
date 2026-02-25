pipeline {
    agent any // In a real enterprise, you'd use a specific label like 'agent { label "automation-node" }'

    parameters {
        choice(name: 'ENVIRONMENT', choices: ['qa', 'dev', 'staging'], description: 'Select Environment')
        string(name: 'BROWSER', defaultValue: 'chrome', description: 'Browser to run tests')
        booleanParam(name: 'HEADLESS', defaultValue: true, description: 'Run in headless mode?')
    }

    stages {
        stage('Cleanup') {
            steps {
                echo "Cleaning up workspace..."
                cleanWs()
            }
        }

        stage('Checkout') {
            steps {
                checkout scm
            }
        }

        stage('Execute Tests') {
            steps {
                script {
                    // We pass Jenkins parameters directly into your Maven command
                    sh "mvn clean test -Denv=${params.ENVIRONMENT} -Dbrowser=${params.BROWSER} -Dheadless=${params.HEADLESS}"
                }
            }
        }
    }

    post {
        always {
            // Archive the Extent Report so it's visible in the Jenkins UI
            publishHTML(target: [
                allowMissing: false,
                alwaysLinkToLastBuild: true,
                keepAll: true,
                reportDir: 'target/ExtentReport',
                reportFiles: 'SparkReport.html',
                reportName: 'Automation Test Report'
            ])
        }
        failure {
            echo "Tests failed! Sending notification..."
            // Enterprise tip: Add Slack or Email notifications here
        }
    }
}