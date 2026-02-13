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
