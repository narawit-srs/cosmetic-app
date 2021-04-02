pipeline {

  environment {
    registry = "narawitrt/cosmeticapi"
    registryCredential = 'dockerhub'
  }

  agent any

  stages {

    stage('Checkout Source') {
      steps {
        git url:'https://github.com/narawit-srs/cosmetic-app.git', branch:'main'
      }
    }

    stage('build java app') {
      steps {
        sh 'mvn -f pom.xml clean package'
      }
    }

    stage('who am i') {
      steps {
        sh 'whoami'
      }
    }

    stage('Building image') {
      steps{
        script {
          dockerImage = docker.build registry + ":$BUILD_NUMBER"
        }
      }
    }

    stage('upload Image to registry') {
      steps{
        script {
          docker.withRegistry( '', registryCredential ) {
            dockerImage.push()
          }
        }
      }
    }

    stage('Remove Unused docker image') {
      steps{
        sh "docker rmi $registry:$BUILD_NUMBER"
      }
    }



    node(kubepod) {

        stage('Checkout Source') {
      steps {
        git url:'https://github.com/narawit-srs/cosmetic-app.git', branch:'main'
      }
    }

      stage('Deploy App') {
          steps {
            script {
              kubernetesDeploy(configs: "cosmetic.yaml", kubeconfigId: "mykubeconfig")
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

}
