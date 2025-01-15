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
stage('Sonarqube Analysis'){
        environment {
            scannerHome = tool 'sonarscanner4' //added in the global tools configuration
        }
        steps{
            withSonarQubeEnv('jenkin-sonarqube'){ //installationName: 
            //sh ''' cd annonce-service && mvn clean org.sonarsource.scanner.maven:sonar-maven-plugin:3.7.0.1746:sonar '''
            sh ''' cd annonce-service && ${scannerHome}/bin/sonar-scanner -Dsonar.projectKey=Hydatys-Devops-Project\
            -Dsonar.projectName=Hydatys-Devops-Project\
            -Dsonar.projectVersion=1.0 \
            -Dsonar.sources=src/ \
            -Dsonar.java.binaries=target/ '''
            }
            /*timeout(time: 5 , unit: 'MINUTES') { // time to wait for qualityGates responese.
                waitForQualityGate abortPipeline: true
            }*/
       }
              /*post {
        always {
            slackSend channel: 'ahmed-jenkins-notifications', message: "Sonarqube Code Analysis -> pipeline status : ${currentBuild.currentResult} ${env.JOB_NAME} ${env.BUILD_URL}" 
        }
    }*/
    }
    
       
    stage ('Upload artifacts to Jfrog Artifactory ') {
            steps {
            // Delete old artifacts
                rtUpload (
                    serverId: 'jfrog-artifactory',
                    spec: '''{
                          "files": [
                            {
                              "pattern": "**/target/*.jar",
                              "target": "ats-project-artifacts"
                            }
                         ]
                    }''',
                )
            }
            /*post {
        always {
            slackSend channel: 'ahmed-jenkins-notifications', message: "Upload artifacts to Jfrog Artifactory  -> pipeline status : ${currentBuild.currentResult} ${env.JOB_NAME} ${env.BUILD_URL}" 
          }
       }*/ 
    }

stage('Build Docker Image') {
      steps {
        sh "ls annonce-service" 
        sh "cd annonce-service && docker build -t annonce-service${BUILD_NUMBER} ."
        sh 'docker tag annonce-service${BUILD_NUMBER} ahmeddaoudi/ats-project-repository:annonce-service${BUILD_NUMBER}'
      }
      /*post {
        always {
            slackSend channel: 'ahmed-jenkins-notifications', message: "Build Docker Image -> pipeline status : ${currentBuild.currentResult} ${env.JOB_NAME} ${env.BUILD_URL}" 
          }
       }*/
    }
    
     stage('Scan artifacts and docker images using trivy ') {
            steps {
                // Install trivy
                sh 'curl -sfL https://raw.githubusercontent.com/aquasecurity/trivy/main/contrib/install.sh | sh -s -- -b /usr/local/bin v0.18.3'
                sh 'curl -sfL https://raw.githubusercontent.com/aquasecurity/trivy/main/contrib/html.tpl > html.tpl'
                
                // test vuln with trivy
                sh 'trivy --no-progress annonce-service${BUILD_NUMBER} '
            }
    }
stage('Connect to Docker-Hub') {
           steps {
                withCredentials([usernamePassword(credentialsId: 'dockerhub_password', usernameVariable: 'USERNAME', passwordVariable: 'PASSWORD')]) {
            sh 'docker login -u $USERNAME -p $PASSWORD'
                        }
                    }
                    /*post {
        always {
            slackSend channel: 'ahmed-jenkins-notifications', message: " Connect to Docker-Hub -> pipeline status : ${currentBuild.currentResult} ${env.JOB_NAME} ${env.BUILD_URL}" 
          }
       }*/
                }
            
    stage("Push Image to Docker Hub"){
            steps{
        sh 'docker push ahmeddaoudi/ats-project-repository'
        echo 'Image pushed'
    }
      /*post {
        always {
            slackSend channel: 'ahmed-jenkins-notifications', message: " Push Image to Docker Hub -> pipeline status : ${currentBuild.currentResult} ${env.JOB_NAME} ${env.BUILD_URL}" 
          }
       
    }*/
    }

    stage('Remove Tagged Images') {
     steps {
        sh 'docker rmi ahmeddaoudi/ats-project-repository:annonce-service${BUILD_NUMBER}'
        sh 'docker rmi annonce-service${BUILD_NUMBER}'
    }
     /*post {
        always {
            slackSend channel: 'ahmed-jenkins-notifications', message: "Remove Tagged Images -> pipeline status : ${currentBuild.currentResult} ${env.JOB_NAME} ${env.BUILD_URL}" 
          }
       
    }*/
    }
  stage('Kubernetes Update') {
    steps {
        echo "Triggering updateKubernetesFile job"
        build job: 'updateKubernetesFile', parameters: [string(name: 'DOCKERTAG', value: "${BUILD_NUMBER}")]
    }
  }
    
   }
}