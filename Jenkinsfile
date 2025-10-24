@Library('jenkins-shared-library')
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
                    BRANCH_NAME == 'master' || BRANCH_NAME == 'jenkins-shared-lib'
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
                    BRANCH_NAME == 'master' || BRANCH_NAME == 'jenkins-shared-lib'
                }
            }
            steps {
                script {
                    buildImage 'gpenieljacobpaul/docker-java-maven-app:3.0'
                    loginDocker()

                }
            }
        }
        stage ("push image") {
            steps {
                script {
                    pushImage 'gpenieljacobpaul/docker-java-maven-app:3.0'
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
