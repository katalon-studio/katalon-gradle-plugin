pipeline {
    agent any
    stages {
        stage ("Build") {
            agent {
                dockerfile {
                    filename 'Dockerfile'
                }
            }
            environment {
                GRADLE_PORTAL = credentials('gradle-portal')
            }
            steps {
                sh 'gradle publishPlugins -Pgradle.publish.key=JU1FbIjlmM0xALMw5EgcNv5F11bnpn1o -Pgradle.publish.secret=9z2Bqb8wIktt6IjY6rHcX9xe3qYiWFCw'
'
            }
        }
    }
}
