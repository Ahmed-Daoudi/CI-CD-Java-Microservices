# CI-CD-Java-Microservices

## Objective

The objective of this documentation is to create a complete CI/CD pipeline for a microservices Spring Boot application using Jenkins pipelines and GitHub as the version control system. This pipeline ensures automated code integration, testing, and deployment, enhancing reliability and efficiency in software development.

---

## What You Will Need

- Linux system (Ubuntu)
- GitHub
- Docker & Docker Compose
- Minikube
- Kubectl
- Vagrant
- VirtualBox
- JUnit
- Trivy
- ArgoCD
- Slack

---

## Project Architecture :
Global architecture of the Project:

 ![Global Architecture](./Images/Global%20Architecture.png)
 ## Configuring Jenkins CI Pipeline

### Integrating Jenkins with GitLab Repository

1. Generate a GitLab access token in your repository under `Settings` >> `Access Tokens`. Ensure the token has the 'Owner' role with the 'api' scope.
2. Add the token to Jenkins credentials via `Manage Jenkins` >> `Credentials` >> `Add new credential`.
3. Create a new pipeline in Jenkins (`New Item` >> `Pipeline`) for your microservice.
4. Use a pipeline script to integrate Jenkins with GitLab, ensuring changes in the repository trigger the CI/CD process.

### Building the Service with Maven

1. Install and configure Maven in Jenkins (`Manage Jenkins` >> `Manage Plugins` and `Global Tool Configuration`).
2. Add Maven build stages to your pipeline for `clean` and `package` goals.

### Running Unit Tests with JUnit

1. Ensure JUnit dependencies are added to your Maven `pom.xml` file.
2. Add a stage in the Jenkins pipeline to execute JUnit tests.
3. Configure Jenkins to collect and display JUnit test reports.

### Integrating Jenkins with SonarQube

1. Set up a SonarQube server and create a project.
2. Add SonarQube credentials in Jenkins (`Manage Jenkins` >> `Credentials`).
3. Configure SonarQube under `Manage Jenkins` >> `Configure System`.
4. Add a SonarQube analysis stage to your Jenkins pipeline.

### Integrating Jenkins with JFrog Artifactory

1. Set up a JFrog Artifactory server and create a Maven repository.
2. Add JFrog credentials and configure the server in Jenkins.
3. Add a stage to upload Maven artifacts to JFrog Artifactory.



