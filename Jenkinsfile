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
        stage("Increament version") {
            steps {
                script {

                    echo 'Increment app version yo!'
                    sh '''
                    mvn build-helper:parse-version versions:set \
                    -DnewVersion=${parsedVersion.majorVersion}.${parsedVersion.minorVersion}.${parsedVersion.nextIncrementalVersion} \
                    versions:commit
                    '''
                    def matcher = readFile('pom.xml') =~ '<version>(.+)</version>'    
                    def version = matcher[0][1]
                    env.IMAGE_NAME = "$version-$BUILD_NUMBER"
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
                    gv.buildJar()
                    echo "ADDING WEBHOOKS"
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
                    gv.buildImage()
                }

            }
        }
        stage("deploy") {
            steps {
                script {
                    echo "deploying the application webhooks added"
                }
            }
        }
    }
}