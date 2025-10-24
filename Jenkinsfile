@library('jenkins-shared-library')
def gv
pipeline {
    agent any
    tools {
        maven 'Maven'
    }
    stages {
        stage("Init") {
            steps {
                script {
                    gv = load "script.groovy"
                }
            }
        }
        stage("build jar") {
            when {
                expression {
                    BRANCH_NAME == 'master'
                }
            }
            steps {
                script {
                    buildJar()
                }
            }
        }
        stage ("build image") {
            when {
                expression {
                    BRANCH_NAME == 'master'
                }
            }
            steps {
                script {
                    buildImage()
                }

            }
        }
        stage("deploy Image") {
            steps {
                script {
                    gv.delpoyImage()
                }
            }
        }
    }
}
