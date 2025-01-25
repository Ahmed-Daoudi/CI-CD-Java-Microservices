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

### Building Docker Images and Pushing to Docker Hub

1. Configure Docker as a global tool in Jenkins.
2. Add stages to build and tag Docker images.
3. Push images to a private Docker Hub repository.

### Scanning Docker Images with Trivy

1. Install Trivy as a global tool on your Jenkins server.
2. Add a stage in the Jenkins pipeline to scan Docker images for vulnerabilities using Trivy.
3. Ensure the pipeline halts on critical vulnerabilities for enhanced security.

### Kubernetes Cluster Setup with Vagrant and kubeadm

This guide explains how to set up a Kubernetes cluster using **Vagrant** and **kubeadm**.

---

#### Prerequisites

1. Install:
   - **Vagrant**
   - **VirtualBox**
   - **git**
2. System requirements:
   - 8 GB RAM, 4 CPUs, 20 GB disk space.

---

#### Project Structure

```
vagrant-kubeadm/
├── configs/          # Configuration files
├── scripts/          # Automation scripts
│   ├── common.sh     # Common setup for all nodes
│   ├── master.sh     # Master node initialization
│   ├── node.sh       # Worker node setup
│   ├── dashboard.sh  # Optional: Deploy Kubernetes Dashboard
├── settings.yaml     # Cluster settings
├── Vagrantfile       # VM definitions
```

---

#### Steps to Set Up the Cluster

1. **Clone the repository**:  
   ```bash
   git clone <repository_url>
   cd vagrant-kubeadm-kubernetes
   ```

2. **Start VMs**:  
   ```bash
   vagrant up
   ```

3. **Initialize the Master Node**:  
   SSH into the master and run:  
   ```bash
   vagrant ssh k8s-master
   sudo bash /vagrant/scripts/master.sh
   ```

4. **Join Worker Nodes**:  
   SSH into each worker node and run:  
   ```bash
   vagrant ssh k8s-worker-1
   sudo bash /vagrant/scripts/node.sh
   ```

   Repeat for `k8s-worker-2`.

5. **Verify the Cluster**:  
   On the master node, check the nodes:  
   ```bash
   kubectl get nodes
   ```

---

#### Optional: Deploy Kubernetes Dashboard

To deploy the dashboard, run on the master:  
```bash
sudo bash /vagrant/scripts/dashboard.sh
```

---

Your Kubernetes cluster is ready to use!