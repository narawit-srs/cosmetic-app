pipeline {

  environment {
    registry = "narawitrt/cosmeticapi"
    registryCredential = 'dockerhub'
  }

  agent any



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



    // stage('Deploy App') {

    //   agent {
    //     label 'kubepod'
    //   }
      // steps {
      //   script {
      //     kubernetesDeploy(configs: "cosmetic.yaml", kubeconfigId: "mykubeconfig")
      //   }
      // }
    // }

podTemplate {
    node('kubepod') {
        stage('Deploy App') {
            steps {
                script {
                  kubernetesDeploy(configs: "cosmetic.yaml", kubeconfigId: "mykubeconfig")
                }
            }
        }
    }
}



    // stage('Deploy App') {
    //   steps {
    //     script {
    //       kubernetesDeploy(configs: "cosmetic.yaml", kubeconfigId: "mykubeconfig")
    //     }
    //   }
    // }

  

}