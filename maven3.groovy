pipeline {
    agent {label "slave"}

    stages {
        stage('Git-Pull-Stage') {
            steps {
                git branch: 'main', url: 'https://github.com/Anilbamnote/student-ui-app.git'
                echo 'Pull success'
            }
        }

        stage('Build-Stage') {
            steps {
                
                sh '/opt/maven/bin/mvn clean install'
                echo 'build stage success'
            }
        }

        stage('Test-Stage') {
            steps {
                echo 'Test Success'
            }
        }

        stage('Deploy-Stage') {
            steps {
                echo 'Deploy Success'
            }
        }
    }
}
