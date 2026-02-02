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
            steps {withSonarQubeEnv(installationName:'sonar', credentialsId: 'sonar-cred') {
                         sh '/opt/maven/bin/mvn sonar:sonar'
            }   

            timeout(10) {
    // some block
            }

            waitForQualityGate true



//                 sh '''/opt/maven/bin/mvn sonar:sonar \\
//   -Dsonar.projectKey=student-app \\
//   -Dsonar.host.url=http://35.177.225.24:9000 \\
//   -Dsonar.login=d4ace945a863cff3d918bb35875aba124d5273c9'''
            }
        }
        
        
        // stage('Artifact_upload') {
        //     steps {
        //         sh '''aws s3 cp target/studentapp-2.2-SNAPSHOT.war s3://my-project-215431/ '''
        //     }
        // }

        stage('Deploy-Stage') {
            steps {

                deploy adapters: [tomcat9(alternativeDeploymentContext: '', credentialsId: 'tomcat-cred', path: '', url: 'http://172.31.3.251:8080')], contextPath: '/', war: '**/*.war'
                echo 'Deploy Success'
            }
        }
    }
}
