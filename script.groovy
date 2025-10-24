def buildJar() {
    echo "building the application..."
    sh 'mvn package'
} 

def buildImage() {
    echo "building the docker image..."
    withCredentials([usernamePassword(credentialsId: 'docker', passwordVariable: 'PASS', usernameVariable: 'USER')]) {
        sh 'docker build -t gpenieljacobpaul/docker-java-maven-app:5.0 .'
        sh "echo $PASS | docker login -u $USER --password-stdin"
        sh 'docker push gpenieljacobpaul/docker-java-maven-app:5.0'
        }
    }

return this
