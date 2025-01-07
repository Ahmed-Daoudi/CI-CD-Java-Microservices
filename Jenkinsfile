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
   }
}