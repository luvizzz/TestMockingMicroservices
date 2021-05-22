# Lease service

A simple crud service which connects to a postgres database and stores information related to a customer's leases

Swagger file available under `http://<server-location>:<server-port>/swagger-ui.html`

Operations available:
- GET /customer/{customer}/lease
- GET /customer/{customer}/lease/mostRecent
- GET /customer/{customer}/lease/{lease}/payment
- POST /customer/{customer}/lease
- POST /customer/{customer}/lease/{lease}/payment
- DELETE /customer/{customer}/lease
- DELETE /customer/{customer}/lease/{leaseId}
- DELETE /customer/{customer}/lease/{lease}/payment
- DELETE /customer/{customer}/lease/{lease}/payment/{paymentId}

## How to start lease-service

- Using terminal, navigate to lease-service root folder: `cd ~/git/TestMockingMicroservices/lease-service`
- Create a jar file to be executed using maven: `mvn clean package`
- Start a postgres instance of the database: `docker run -d -p 5433:5432 -e POSTGRES_PASSWORD=postgres postgres`
- Start the jar file `java -jar target/lease-service-1.0-SNAPSHOT.jar`

## How to generate docker image of lease-service

On a terminal:
- Make sure docker is running (by executing `docker ps`).
- Change directory to the root folder of customer service (e.g. `cd ~/git/TestMockingMicroservices/lease-service`
- Create a new local docker image: `docker build -t lease-service-1.0 -f src/main/resources/Dockerfile .`
