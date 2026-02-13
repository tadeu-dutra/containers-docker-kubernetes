# Unit 4 - Creating a Java API + Mongo DB project using Docker Compose

## Overview

This project runs a REST API and MongoDB using Docker Compose.

## Learning Objectives

The main goal of this project is to:

- Understand basic container orchestration concepts
- Learn how Docker Compose manages multiple containers
- Practice running a Java API connected to a database using containers

## Architecture

Client → Spring Boot Container → MongoDB Container

## Technologies

  - Java 21
  - Spring Boot 3
  - MongoDB 8
  - Docker
  - Docker Compose

## Configuration

Environment variables used:

  spring.data.mongodb.host=${SPRING_DATA_MONGODB_HOST:mongo}
  spring.data.mongodb.port=${SPRING_DATA_MONGODB_PORT:27017}
  spring.data.mongodb.database=${SPRING_DATA_MONGODB_DATABASE:mydb}

## How to Run

  cd unit-4/test-api
  docker compose up --build

Access API

  http://localhost:8080

## Testing

cURL examples

  GET endpoint

  ```
  curl --request GET \
  --url http://localhost:8080/message \
  --header 'Content-Type: application/json'
  ```

  POST endpoint
  ```
  curl --request POST \
  --url http://localhost:8080/message \
  --header 'Content-Type: application/json' \
  --data '{
    "message": "how can I help you?"
  }'
  ```

## Troubleshooting

Rebuild Docker images:

  docker compose down -v --remove-orphans
  docker network prune -f
  docker compose build --no-cache
  docker compose up