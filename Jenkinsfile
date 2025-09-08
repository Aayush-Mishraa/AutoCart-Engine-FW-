pipeline {
    agent any
    
    tools {
        maven 'maven'
    }

    stages {
        stage('Build') {
            steps {
                git 'https://github.com/jglick/simple-maven-project-with-tests.git'
                bat "mvn -Dmaven.test.failure.ignore=true clean package"
            }
            post {
                success {
                    junit '**/target/surefire-reports/TEST-*.xml'
                    archiveArtifacts 'target/*.jar'
                }
            }
        }
        
        stage("Deploy to QA") {
            steps {
                echo "deploy to QA done"
            }
        }
        
        stage('Regression Automation Tests') {
            steps {
                catchError(buildResult: 'SUCCESS', stageResult: 'FAILURE') {
                    git 'https://github.com/Aayush-Mishraa/AutoCart-Engine-FW-.git'
                    bat "mvn clean test -Dsurefire.suiteXmlFiles=src/test/resources/testrunner/testing_regression.xml"
                }
            }
        }
        
        stage('Publish Allure Reports') {
            steps {
                script {
                    allure([
                        includeProperties: false,
                        jdk: '',
                        properties: [],
                        reportBuildPolicy: 'ALWAYS',
                        results: [[path: 'allure-results']]
                    ])
                }
            }
        }
        
        stage('Publish ChainTest Report') {
            steps {
                publishHTML([
                    allowMissing: false,
                    alwaysLinkToLastBuild: false,
                    keepAll: true,
                    reportDir: 'target/chaintest',
                    reportFiles: 'Index.html',
                    reportName: 'HTML Regression ChainTest Report',
                    reportTitles: ''
                ])
            }
        }
        
        stage("Deploy to Stage") {
            steps {
                echo "deploy to Stage"
            }
        }
        
        stage('Sanity Automation Test') {
            steps {
                catchError(buildResult: 'SUCCESS', stageResult: 'FAILURE') {
                    git 'https://github.com/Aayush-Mishraa/AutoCart-Engine-FW-.git'
                    bat "mvn clean test -Dsurefire.suiteXmlFiles=src/test/resources/testrunner/test_sanity.xml"
                }
            }
        }
        
        stage('Publish sanity ChainTest Report') {
            steps {
                publishHTML([
                    allowMissing: false,
                    alwaysLinkToLastBuild: false,
                    keepAll: true,
                    reportDir: 'target/chaintest',
                    reportFiles: 'Index.html',
                    reportName: 'HTML Sanity ChainTest Report',
                    reportTitles: ''
                ])
            }
        }
        
        stage("Deploy to PROD") {
            steps {
                echo "deploy to PROD"
            }
        }
    }
}
