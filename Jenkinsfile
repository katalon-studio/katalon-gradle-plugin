pipeline {
    agent {
        dockerfile {
            filename 'Dockerfile'
        }
    }
    stages {
        stage ("Build") {
            when { branch 'master' }
            environment {
                GRADLE_PORTAL = credentials('gradle-portal')
            }
            steps {
                sh 'gradle publishPlugins -Pgradle.publish.key=$GRADLE_PORTAL_USR -Pgradle.publish.secret=$GRADLE_PORTAL_PSW'
            }
        }
    }
}
