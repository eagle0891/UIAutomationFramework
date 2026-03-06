pipeline {
    agent any // In a real enterprise, you'd use a specific label like 'agent { label "automation-node" }'

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
                    // Runs ONLY tests tagged with @api
                    // Use clean here to start fresh
                    //sh "mvn test -Dcucumber.filter.tags='@api' -Denv=${params.ENVIRONMENT}"
                    sh "mvn clean test -Dcucumber.filter.tags='@api' -Denv=${params.ENVIRONMENT}"
                }
            }

            stage('UI Regression') {
                steps {
                    echo "API Passed. Running UI Layer for ${params.ENVIRONMENT} on ${params.BROWSER}..."
                    // Runs ONLY tests tagged with @ui
                    // DO NOT use clean here, or you'll delete the API results from the report
                    sh "mvn test -Dcucumber.filter.tags='@ui' -Denv=${params.ENVIRONMENT} -Dbrowser=${params.BROWSER} -Dheadless=${params.HEADLESS}"
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