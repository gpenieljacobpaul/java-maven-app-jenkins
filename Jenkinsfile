def gv
pipeline {
    agent any
    tools {
        maven 'Maven'
    }
    stages {
        state("Init") {
            steps {
                script {
                    gv = load "script.groovy"
                }
            }
        }
        stage("build jar") {
            steps {
                script {
                    gv.buildJar()
                }
            }
        }
        stage ("build image") {
            steps {
                script {
                    gv.buildImage()
                }

            }
        }
        stage("deploy") {
            steps {
                script {
                    echo "deploying the application..."
                }
            }
        }
    }
}