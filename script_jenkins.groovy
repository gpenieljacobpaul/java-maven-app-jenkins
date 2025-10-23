def build_app() {
    echo 'Building the application...'
    echo "Building version ${NEW_VERSION}"
    sh "mvn clean package"
}

def test_app() {
    echo 'Testing the application...'
}


return this
