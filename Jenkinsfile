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
        // stage ("Push pom.xml to gitbub repo") {
        //     steps {
        //         script {
        //             withCredentials([usernamePassword(credentialsId: 'gitpush', passwordVariable: 'GIT_PASS', usernameVariable: 'GIT_USER')]) {
        //                 sh 'git config user.email "jenkins@gmail.com"'
        //                 sh 'git config user.name "jenkins"'
        //                 sh 'git status'
        //                 sh 'git branch'
        //                 sh 'git config --list'
        //                 sh "git remote set-url origin https://${GIT_USER}:${GIT_PASS}@github.com/gpenieljacobpaul/java-maven-app-jenkins.git"
        //                 sh 'git add .'
        //                 sh 'git commit -m "cli: version bump webooks [ci skip]"'
        //                 sh 'git push origin HEAD:feature'
        //             }
        //         }
        //     }
        // }
        stage("deploy") {
            steps {
                script {
                    echo "deploying"
                    def dockerCmd = "docker run -d -p 8082:8082 gpenieljacobpaul/docker-java-maven-app:${IMAGE_NAME}"
                    //gv.deployApp()
                    sshagent(['ec2-server-key']) {
                        sh "ssh -o StrictHostKeyChecking=no ubuntu@13.203.97.225 ${dockerCmd}"
                    }    
                }   
            }
        }
    }
}