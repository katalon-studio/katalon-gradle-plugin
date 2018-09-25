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
                sh 'gradle publishPlugins -Pgradle.publish.key=$GRADLE_PORTAL_USR -Pgradle.publish.secret=$GRADLE_PORTAL_PSW'
            }
        }
    }
}
