pipeline {

  environment {
    registry = "narawitrt/cosmeticapi"
    registryCredential = 'dockerhub'
  }

  agent { label 'kubepod' }

  stages {

    // stage('Checkout Source') {
    //   steps {
    //     git url:'https://github.com/narawit-srs/cosmetic-app.git', branch:'main'
    //   }
    // }

    // stage('build java app') {
    //   steps {
    //     sh 'mvn mvn -f pom.xml clean package'
    //   }
    // }

    // stage('Building image') {
    //   steps{
    //     script {
    //       dockerImage = docker.build registry + ":$BUILD_NUMBER"
    //     }
    //   }
    // }

    // stage('Deploy Image') {
    //   steps{
    //     script {
    //       docker.withRegistry( '', registryCredential ) {
    //         dockerImage.push()
    //       }
    //     }
    //   }
    // }

    // stage('Remove Unused docker image') {
    //   steps{
    //     sh "docker rmi $registry:$BUILD_NUMBER"
    //   }
    // }

    // stage('Deploy App') {
    //   steps {
    //     script {
    //       kubernetesDeploy(configs: "cosmetic.yaml", kubeconfigId: "mykubeconfig")
    //     }
    //   }
    // }


  podTemplate(containers: [
    containerTemplate(name: 'maven', image: 'maven:3.3.9-jdk-8-alpine', ttyEnabled: true, command: 'cat'),
    containerTemplate(name: 'golang', image: 'golang:1.8.0', ttyEnabled: true, command: 'cat')
  ]) {

    node(kubepod) {
        stage('Get a Maven project') {
            git 'https://github.com/jenkinsci/kubernetes-plugin.git'
            container('maven') {
                stage('Build a Maven project') {
                    sh 'mvn -B clean install'
                }
            }
        }

        stage('Get a Golang project') {
            git url: 'https://github.com/hashicorp/terraform.git'
            container('golang') {
                stage('Build a Go project') {
                    sh """
                    mkdir -p /go/src/github.com/hashicorp
                    ln -s `pwd` /go/src/github.com/hashicorp/terraform
                    cd /go/src/github.com/hashicorp/terraform && make core-dev
                    """
                }
            }
        }

    }
}


  }

}
