pipeline {
    agent {
        docker {
            image 'gradle'
            args '$PWD:/home/gradle/project -w /home/gradle/project'
        }
    }
    stages {
        stage ("Build") {
            when {
                not {
                    branch 'master'
                }
            }
            steps {
                sh 'gradle clean build'
            }
        }

        stage ("Build and publish") {
            when {
                branch 'master'
            }
            environment {
                GRADLE_PORTAL = credentials('gradle-portal')
            }
            steps {
                sh 'gradle clean build publishPlugins -Pgradle.publish.key=$GRADLE_PORTAL_USR -Pgradle.publish.secret=$GRADLE_PORTAL_PSW'
            }
        }
    }
}
