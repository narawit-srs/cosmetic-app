pipeline {

  environment {
    registry = "narawitrt/cosmeticapi"
    registryCredential = 'dockerhub'
  }

  agent none

  stages {

    stage('Checkout Source') {
      agent any
      steps {
        git url: 'https://github.com/narawit-srs/cosmetic-app.git', branch: 'main'
      }
    }

    stage('build java app') {
      agent any
      steps {
        sh 'mvn -f pom.xml clean package'
      }
    }

    stage('who am i') {
      agent any
      steps {
        sh 'whoami'
      }
    }

    stage('Building image') {
      agent any
      steps {
        script {
          dockerImage = docker.build registry + ":$BUILD_NUMBER"
        }
      }
    }

    stage('upload Image to registry') {
      agent any
      steps {
        script {
          docker.withRegistry('', registryCredential) {
            dockerImage.push()
          }
        }
      }
    }

    stage('Remove Unused docker image') {
      agent any
      steps {
        sh "docker rmi $registry:$BUILD_NUMBER"
      }
    }


    stage('Deploy App') {

      agent { 
          label 'kubepod'
      }
      steps {
        script {
          kubernetesDeploy(configs: "cosmetic.yaml", kubeconfigId: "mykubeconfig")
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

//   podTemplate {
//     node('kubepod') {
//         stage('Run shell') {
//             sh 'echo hello world'
//         }
//     }
// }

}