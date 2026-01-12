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
                // sh 'mvn clean install'
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
