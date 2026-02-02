//basic code

// pipeline {
//     agent any

//     stages {
//         stage('git-pull-stage') {
//             steps {
//                 echo 'Pull success'
//             }
//         }

//         stage('build-stage') {
//             steps {
//                 echo 'Build stage success'
//                 // sh 'mvn clean install'
//             }
//         }

//         stage('test-stage') {
//             steps {
//                 echo 'test success'
                
//             }
//         }

//         stage('deploy-stage') {
//             steps {
//                 echo 'Deploy succes'
            
//             }
//         }
//     }
// } 




//1st git-push


pipeline {
    agent any

    stages {
        stage('git-pull-stage') {
            steps {
                echo 'Pull success'
                git branch: 'main', url: 'https://github.com/Anilbamnote/cdec-46.git'
            }
        }

        stage('build-stage') {
            steps {
                echo 'Build stage success'
                
            }
        }

        stage('test-stage') {
            steps {
                echo 'test success'
                
            }
        }

        stage('deploy-stage') {
            steps {
                echo 'Deploy succes'
            
            }
        }
    }
}


//2nd build-stage (maven)
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

//3rd  test-stage (Sonar-Quality-gate)

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

        stage('Deploy-Stage') {
            steps {
                echo 'Deploy Success'
            }
        }
    }
}



//4th  Artifact-stage (artifact upload in S3)

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
        
        
        stage('Artifact_upload') {
            steps {
                sh '''aws s3 cp target/studentapp-2.2-SNAPSHOT.war s3://my-project-215431/ '''
            }
        }

        stage('Deploy-Stage') {
            steps {
                echo 'Deploy Success'
            }
        }
    }
}


//5th deploy-stage (deploy on tomcat)



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

  
