# Unit 4 - Kubernetes

## Overview

This project deploys [the same application of the previous unit](../unit-4/README.md) into a Kubernetes cluster using Minikube.

## Learning Objectives

  - Deploy a multi-container application into a Kubernetes cluster using Minikube.
  - Understand and implement core Kubernetes resources (Pods, Deployments and Services).
  - Expose internal cluster applications externally using NodePort
  - Enable secure internal communication using ClusterIP
  - Demonstrate horizontal scaling by configuring multiple replicas in a Deployment and observing Kubernetes’ self-healing behavior.

## Architecture

    Client → NodePort → Service → Pod → Service → MongoDB Pod

## Technologies

  - Kubernetes
  - Minikube
  - kubectl
  - Docker

## Configuration

Environment variables injected in Deployment:

    ```
    env:
        - name: SPRING_DATA_MONGODB_URI
          value: mongodb://database-service:27017/mydb
        - name: SPRING_DATA_MONGODB_HOST
          value: database-service
        - name: SPRING_DATA_MONGODB_PORT
          value: "27017"
        - name: SPRING_DATA_MONGODB_DATABASE
          value: mydb
    ```

## How to Run

Make sure you access the root project folder:
  cd unit-5/test-api

Start cluster:
  minikube start

Apply manifests:
  kubectl apply -f pods/
  kubectl apply -f deployments/
  kubectl apply -f services/

Access API:
  minikube service test-api-service --url

## Testing

cURL examples

  GET endpoint

  ```
  curl --request GET \
  --url http://127.0.0.1:38667/message \
  --header 'Content-Type: application/json'
  ```

  POST endpoint
  ```
  curl --request POST \
  --url http://127.0.0.1:38667/message \
  --header 'Content-Type: application/json' \
  --data '{
    "message": "how can I help you?"
  }'
  ```
NOTE: This port number is dynamic and changes on new deployments.

## Troubleshooting

Checking logs:

    kubectl logs <pod>

Restarting cluster:

    minikube delete --all --purge
    docker system prune -f
    minikube start --driver=docker
    kubectl get nodes -A






Kubernetes (or k8s) is an advanced tool designed to manage clusters of servers, providing a robust infrastructure for production environments.

While Docker Compose excels at automating the setup of multiple containers on one machine, Kubernetes is built to manage large-scale clusters, providing advanced features for scaling, resilience, and complex networking.

## Prerequisites for this Unit

The goal of this unit is to make the `test-api` project created in the Unit 5 run on a Kubernetes cluster. So, the prerequisites are:

1. test-api project (test-api and mongo containers)
2. Kubernetes running (for this project we use MiniKube)

NOTE: with you Docker running and MiniKube installed, run the command `minikube start` to start MiniKube before start setting the deployment configurations.

## Kubernetes configuration

1. **pod-definition.yml**: Pod definition for the `test-api` Docker image created in the previous Unit
2. **database-deployment-definition.yml**: Database deployment configuration. As it is a demo project, only one replica will be set.
3. **test-api-deployment-definition.yml**: Application deployment configuration. This deployment contains the database mapping (database-service) and will provide 5 replicas of our application.

All the three deployment definitions yaml files can be created by running the following command:
$ kubectl create -f <pod_definition_filename>.yml

To see the created pods just run:
$ kubectl get pods

![alt text](resources/img-console-get-pods.png)

Just for testing purposes, we can run the command below to delete an application pod. Since the deployment definition sets the `replicas` as 5, a new instance of this pod is auto-created:

$ wsl kubectl delete pod test-api-deployment-67b4f6bdf6-jfrwh

## Kubernetes Services

Applications inside Kubernetes run in an internal network.

![alt text](resources/img-services-communication.png)

This means external users cannot access them directly, and internal IP addresses may change over time.

A Service provides a stable way to expose and access applications.

    - ClusterIP – Exposes a service internally within the cluster.
    - NodePort – Exposes a service externally, allowing outside access.

Hence, the following scenario is required:

![alt text](resources/img-services-port-types.png)

Here is an illustration of the `test-api` port mapping:

![alt text](resources/img-services-port-mapping.png)

After deployment the database and the api definitions by using `kubectl create` command, run the command below to see the External IP of our Service.

$ wsl minikube service test-api-service --url

shutdown
wsl kubectl delete -f services/
wsl kubectl delete -f deployments/
wsl kubectl delete -f pods/

wsl kubectl create -f services/
wsl kubectl create -f deployments/
wsl kubectl create -f pods/

wsl kubectl logs test-api
wsl kubectl rollout restart deployment database-deployment
wsl kubectl rollout restart deployment test-api-deployment
wsl kubectl rollout restart svc database-service
wsl kubectl rollout restart svc test-api-service


---

a) Starting a fresh local cluster:
minikube delete --all --purge
docker system prune -f
minikube start --driver=docker
kubectl get nodes -A        #

wsl kubectl apply -f pods/*
wsl kubectl apply -f deployments/d*
wsl kubectl apply -f deployments/t*
wsl kubectl apply -f services/d*
wsl kubectl apply -f services/t*
wsl minikube service test-api-service --url

---

☸️ When you click Kubernetes

Your request flow looks like this:

Insomnia/Postman
      ↓
minikube NodePort / port-forward
      ↓
Kubernetes Service
      ↓
Kubernetes Pod (Spring Boot container)
      ↓
Kubernetes Service
      ↓
Mongo Pod


This is managed by:

Minikube

Kubernetes API

Deployments

Services

Cluster DNS (database-service)

So here you are:

Sending a request to a container running inside a Kubernetes cluster.
