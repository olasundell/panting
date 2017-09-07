pipeline {
  agent any
  stages {
    stage('build') {
      steps {
        sh './gradlew compileJava'
      }
    }
    stage('test') {
      steps {
        sh './gradlew test'
        junit 'build/test-results/test/**/*.xml'
      }
    }
  }
}