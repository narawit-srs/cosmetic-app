pipeline {

  environment {
    registry = "narawitrt/cosmeticapi"
    registryCredential = 'dockerhub'
    sonarProjectKey = "testsonar" 
	  sonarServer = "127.0.0.1:9000"
	  sonarToken = "sonaradmin" // Replace your credential
  }

  agent any

  stages {

    stage('test pararel'){
      parallel {
                stage('Test On master') {
                    steps {
                        echo "test on Master"
                    }
                }
                stage('Test On agent') {
                    agent {
                        label "kubepod"
                    }
                    steps {
                        echo "test on agent"
                    }
                }
            }
    }

    stage('Checkout Source') {
      steps {
        git url: 'https://github.com/narawit-srs/cosmetic-app.git', branch: 'main'
      }
    }

    stage('build java app') {
      steps {
        sh 'mvn -f pom.xml clean package'
      }
    }

    // stage('Sonar Scan') {
    //           steps {
    //               sh '''
    //               pwd
    //               mvn sonar:sonar -Dsonar.projectKey=${sonarProjectKey} -Dsonar.host.url=${sonarServer} -Dsonar.login=${sonarToken} -f pom.xml
    //               chown -hR 989 target/sonar
	  //               chgrp -hR 983 target/sonar
    //               '''
    //               // mvn sonar:sonar -Dsonar.projectKey=${sonarProjectKey} -Dsonar.host.url=${sonarServer} -Dsonar.login=${sonarToken} -f pom.xml
                  
    //           }
    //     }

    stage('Building image') {
      steps {
        script {
          dockerImage = docker.build registry + ":$BUILD_NUMBER"
        }
      }
    }

    stage('upload Image to registry') {
      steps {
        script {
          docker.withRegistry('', registryCredential) {
            dockerImage.push()
          }
        }
      }
    }

    stage('Remove Unused docker image') {
      steps {
        sh "docker rmi $registry:$BUILD_NUMBER"
      }
    }

    stage('Checkout Source for agent and Deploy app') {
      agent { 
          label 'kubepod'
      }
      steps {
        script {
          kubernetesDeploy(configs: "cosmetic.yaml", kubeconfigId: "mykubeconfig")
        }
      }
    }

  }

}