def buildJar() {
    echo "building the application..."
    sh 'mvn clean package'
} 

def buildImage() {
    echo "building the docker image..."
    withCredentials([usernamePassword(credentialsId: 'docker', passwordVariable: 'PASS', usernameVariable: 'USER')]) {
        sh "docker build -t gpenieljacobpaul/docker-java-maven-app:$IMAGE_NAME ."
        sh "echo $PASS | docker login -u $USER --password-stdin"
        sh "docker push gpenieljacobpaul/docker-java-maven-app:$IMAGE_NAME"
        }
    }

return this