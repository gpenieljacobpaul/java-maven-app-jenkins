def gv
pipeline {
    agent any
    tools {
        maven 'Maven'
    }
    triggers {
        githubPush()
    }
    stages {
        stage("Init") {
            steps {
                script {
                    gv = load "script.groovy"
                }
            }
        }
        stage("Increament version ") {
            steps {
                script {
                    echo 'Increment app version '
                    sh 'mvn build-helper:parse-version versions:set -DnewVersion=\\\${parsedVersion.majorVersion}.\\\${parsedVersion.minorVersion}.\\\${parsedVersion.nextIncrementalVersion} versions:commit'
                    def matcher = readFile('pom.xml') =~ '<version>(.+)</version>'    
                    def version = matcher[0][1]
                    env.IMAGE_NAME = "${version}-${BUILD_NUMBER}"
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
        stage ("Docker build image") {
            steps {
                script {
                    gv.buildImage()
                }

            }
        }
        stage("deploy") {
            steps {
                script {
                    echo "deploying to ec2 server using docker compose"
                    def dockerComposeCmd = "docker-compose -f docker-compose.yaml up"
                    //def dockerCmd = "docker run -d -p 8080:8080 gpenieljacobpaul/docker-java-maven-app:${IMAGE_NAME}"
                    //gv.deployApp()
                    def shellCmd = "bash ./server-cmds.sh ${IMAGE_NAME}"
                    def ec2Instance = "ubuntu@3.110.114.80"
                    sshagent(['ec2-server-key']) {
                        sh "scp server-cmds.sh ${ec2Instance}:/home/ubuntu"
                        //sh "ssh -o StrictHostKeyChecking=no ubuntu@3.110.114.80 ${dockerCmd}"
                        sh "scp docker-compose.yaml ${ec2Instance}:/home/ubuntu"
                        //sh "ssh -o  StrictHostKeyChecking=no ${ec2Instance} ${shellCmd}"
                        sh "ssh -o  StrictHostKeyChecking=no ${ec2Instance} ${shellCmd}"
                    }    
                }   
            }
        }
        stage ("Push pom.xml to gitbub repo") {
            steps {
                script {
                    withCredentials([usernamePassword(credentialsId: 'git', passwordVariable: 'GIT_PASS', usernameVariable: 'GIT_USER')]) {
                        sh 'git config user.email "jenkins@gmail.com"'
                        sh 'git config user.name "jenkins"'
                        sh 'git status'
                        sh 'git branch'
                        sh 'git config --list'
                        sh "git remote set-url origin https://${GIT_USER}:${GIT_PASS}@github.com/gpenieljacobpaul/java-maven-app-jenkins.git"
                        sh 'git checkout feature || git checkout -b feature origin/feature'
                        sh 'git add .'
                        sh 'git commit -m "ci: version bump"'
                        sh 'git push origin HEAD:feature'
                    }
                }
            }
        }
    }
}