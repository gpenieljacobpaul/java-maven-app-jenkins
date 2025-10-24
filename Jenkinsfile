pipeline {
    agent any
    tools {
        maven 'Maven'
    }
    stages {
        stage("build jar") {
            steps {
                script {
                    echo "Building the application..."
                    sh 'mvn clean package'
                }
            }
        }
        stage ("build image") {
            steps {
                script {
                    echo "Building docker image..."
                    withCredentials([usernamePassword(credentialsId: 'docker', passwordVariable: 'PASS', usernameVariable: 'USER')]) {
                        sh 'docker build -t gpenieljacobpaul/docker-java-maven-app:2.0 .'
                        sh "echo $PASS | docker login -u $USER --password-stdin"
                        sh 'docker push gpenieljacobpaul/docker-java-maven-app:2.0'
                    }

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