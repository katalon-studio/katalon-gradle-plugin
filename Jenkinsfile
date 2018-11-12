pipeline {
    agent {
        docker {
            image 'gradle'
            args '-v $PWD:/home/gradle/project -w /home/gradle/project'
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
                GRADLE_PUBLISH = credentials('gradle-publish')
            }
            steps {
                sh 'gradle clean build publishPlugins -Pgradle.publish.key=$GRADLE_PUBLISH_USR -Pgradle.publish.secret=$GRADLE_PUBLISH_PSW'
            }
        }
    }
}
