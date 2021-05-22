# Test Mocking Microservices

This project aims to practice usage of testcontainers for integration tests purposes.

## Application overview

### Rental service

Simple crud service which connects to a database and stores information related to a customer's rental history


### Lease service

Simple crud service which connects to a database and stores information related to a customer's lease history


### Customer service

A service which fetches data from both Rental Service and Lease Service, aggregating the response into a single entity


### Integration Test Framework

A module to test if other services are behaving and communicating as expected.

All tests depend on few docker containers which will be brought up at test run initialization.

