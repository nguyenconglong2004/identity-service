# Identity service
This microservice is responsible for:
* Onboarding users
* Roles and permissions
* Authentication

## Tech stack
* Build tool: maven >= 3.9.5
* Java: 21
* Framework: Spring boot 3.2.x
* DBMS: MySQL

## Prerequisites
* Java SDK 21
* A MySQL server

## Start application
`mvn spring-boot:run`

## Build application
`mvn clean package`

## Docker guideline
### Build image:
`docker build -t identify-service:tag .`
### Create network:
`docker network create identify-network`
### Start MySQL in identify-network
`docker run --network identify-network --name mysql -p 3306:3306 -e MYSQL_ROOT_PASSWORD=root -d mysql:8.0.39-debian`
### Run your application in devteria-network
`docker run --name identify-service --network identify-network -p 8080:8080 -e DBMS_CONNECTION=jdbc:mysql://mysql:3306/identify_service identify-service:0.0.1`