# Customer service

A simple aggregation service which calls rental-service and lease-service, aggregating data into a single response representing customer's historical transactions.

Swagger file available under `http://<server-location>:<server-port>/swagger-ui/index.html?configUrl=/v3/api-docs/swagger-config`

Operations available:
- GET /customer/{customer}
- GET /customer/{customer}/mostRecent

##How to start customer-service

- Using terminal, navigate to customer-service root folder: `cd ~/git/TestMockingMicroservices/customer-service`
- Create a jar file to be executed using maven: `mvn clean package`
- Start the jar file `java -jar target/customer-service-1.0-SNAPSHOT.jar`

##How to generate docker image of customer-service

On a terminal:
- Make sure docker is running (by executing `docker ps`).
- Change directory to the root folder of customer service (e.g. `cd ~/git/TestMockingMicroservices/customer-service`
- Create a new local docker image: `docker build -t customer-service-1.0 -f src/main/resources/Dockerfile .`
