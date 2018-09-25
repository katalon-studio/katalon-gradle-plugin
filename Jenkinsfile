pipeline {
    agent any
    stages {
        stage ("Build") {
            agent {
                dockerfile {
                    filename 'Dockerfile'
                }
            }
            steps {
                sh './build.sh'
            }
        }
    }
}