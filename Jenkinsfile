pipeline {
  agent any
  stages {
    stage('build') {
      steps {
        sh './gradlew clean compileJava'
      }
    }
    stage('test') {
      steps {
        sh './gradlew test'
        junit 'build/test-results/test/**/*.xml'
      }
    }
    stage('dockerize') {
      steps {
        sh './gradlew buildDocker'
      }
    }
    stage('deploy') {
      steps {
        sh './gradlew runDocker'
      }
    }
  }
}