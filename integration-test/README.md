# Integration Test Framework

---

## Containerized Tests

Collection of test cases which can be expanded by extending BaseContainerizedTest class.

These tests will bring up several docker containers in order to assert integration.

Containers created by each execution:
- Rental Service Database
- Rental Service Application
- Lease Service Database
- Lease Service Application
- Customer Service Application

### Running Containerized Tests

In order to execute tests which will bring one container per service, it is required to have some docker images available:
- postgres:9.6.12
- lease-service-1.0
- rental-service-1.0
- customer-service-1.0

### How to download postgres:9.6.12 image

On a terminal, make sure docker is running (by executing `docker ps`).
Simply pull the desired image: `docker pull postgres:9.6.12`.
There is no need to start a local container with postgres, as this will be handled internally by the test framework.

### How to generate required services images

On a terminal:
- Make sure docker is running (by executing `docker ps`).
- Change directory to the root folder of the desired service (e.g. `cd ~/git/TestMockingMicroservices/lease-service`
- Create a new local docker image: `docker build -t lease-service-1.0 -f src/main/resources/Dockerfile .`

### Execute tests

Once the images are available, execute the Junit test cases and you will be able to see the containers being started in docker.

---

## Mocked Tests

Collection of test cases which can be expanded by extending BaseMockedTest class.

These tests will bring up one container for Customer service and mock all other applications/responses.

Containers created by each execution:
- Customer Service Application
- MockServer mocking Rental Service
- MockServer mocking Lease Service

Note: this has to be done 3 times, one per each mentioned service (rental-service, lease-service, customer-service)

### Running Mocked Tests

In order to execute tests which will bring one container and mock further communications, it is required to have some docker images available:
- mockserver/mockserver
- customer-service-1.0

### How to download mockserver image

On a terminal, make sure docker is running (by executing `docker ps`).
Simply pull the desired image: `docker pull mockserver/mockserver`.
There is no need to start a local container with mockserver, as this will be handled internally by the test framework.

### How to generate required service image

On a terminal:
- Make sure docker is running (by executing `docker ps`).
- Change directory to the root folder of customer service (e.g. `cd ~/git/TestMockingMicroservices/customer-service`
- Create a new local docker image: `docker build -t customer-service-1.0 -f src/main/resources/Dockerfile .`

### Execute tests

Once the images are available, execute the Junit test cases and you will be able to see the containers being started in docker.
Additionally, the logs within docker containers will show whether the mocking structured has worked or not.
