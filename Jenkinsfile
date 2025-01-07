pipeline {
    
  agent any
  
  tools{
      maven "Maven3"
  }
   stages {

    stage('Git Checkout') {
        steps{
         git branch: 'main', credentialsId: 'gitlab-credentials', url: 'https://gitlab.com/Ahmed_Daoudi/hydatys-devops-project'
            }
        /*post {
        always {
            slackSend channel: 'ahmed-jenkins-notifications', message: "Git Checkout pipeline status : ${currentBuild.currentResult} ${env.JOB_NAME} ${env.BUILD_URL}" 
        }
      }*/
      }
          stage('Clean annonce-service') {
        steps{
        sh 'cd annonce-service && mvn clean'
        }
    }
    
    stage('Build annonce-services') {
        steps{
         sh 'ls -ltr'
         //sh 'java -version'
         sh 'cd annonce-service && mvn clean install -DskipTests ' //package//-Dmaven.test.failure.ignore-true
            }
            post {
                success{
                    echo "Now Archiving Artifacts"
                    archiveArtifacts artifacts: '**/target/*.jar'
                 }
                /*always {
            slackSend channel: 'ahmed-jenkins-notifications', message: "Maven Build -> pipeline status : ${currentBuild.currentResult} ${env.JOB_NAME} ${env.BUILD_URL}" 
        }*/
            }
    }
    
    
    stage ("Test annonce-services"){
        steps{
            sh "cd annonce-service && mvn test"
        }
        /*post {
        always {
            slackSend channel: 'ahmed-jenkins-notifications', message: "Junit Tests -> pipeline status : ${currentBuild.currentResult} ${env.JOB_NAME} ${env.BUILD_URL}" 
        }
    }*/
   }

    stage ("Integration Junits Test for annonce-services"){
        steps {
            sh "cd annonce-service && mvn verify -DskipUnitTests"
        }
         /*post {
        always {
            slackSend channel: 'ahmed-jenkins-notifications', message: "Integration Tests -> pipeline status : ${currentBuild.currentResult} ${env.JOB_NAME} ${env.BUILD_URL}" 
        }
    }*/
   }

   }
}