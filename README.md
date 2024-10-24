# Accreditation Service

## Overview

This project implements a RESTful service for managing accreditation statuses at Yieldstreet. It includes both administrator-facing and client-facing APIs to create and update accreditations. The service is containerized using Docker and utilizes Kafka for event-driven messaging and MySQL as a relational database for persistent storage.

## Architecture Overview

The service follows a **microservices architecture** with the following components:

1. **Spring Boot Application**: This is the core of the application, exposing REST endpoints for accreditation management.
2. **MySQL Database**: Used for storing persistent accreditation data. We follow a typical relational schema with tables for users and accreditations.
3. **Kafka**: Used for event-driven architecture. Whenever an accreditation status is updated, a Kafka event is published to ensure that other services can react to these changes asynchronously.
4. **Zookeeper**: Used for managing Kafka as part of the Confluent stack.
5. **Docker**: All components are containerized using Docker to ensure portability and easy deployment across environments.

The key **architectural patterns** applied include:

- **Event-Driven Architecture**: Kafka is used to decouple services, ensuring that changes in accreditation status are communicated reliably to other parts of the system.
- **Microservices**: The service is designed to be part of a larger ecosystem of independent services, promoting modularity and scalability.
- **Layered Architecture**: The application follows a typical layered structure with controllers, services, repositories, and models, ensuring separation of concerns and easy maintainability.

## Concurrency Handling

### Challenge: Multiple concurrent updates to the same accreditation status by different administrators.

To handle multiple administrators making concurrent updates to the same accreditation status, we can implement the following strategies:

1. **Optimistic Locking**: In our database schema, we can add a version column to the accreditation entity. Every time an update occurs, the version number is incremented. If two administrators attempt to update the same record at the same time, the one with the outdated version will be rejected, ensuring that only the most recent state of the accreditation is updated.
   
   In Spring Data JPA, this can be implemented by adding the `@Version` annotation to the accreditation entity.

   ```java
   @Version
   private Integer version;

2. **Transactional Updates**: Ensure that updates to the accreditation status are wrapped in transactions to maintain atomicity and consistency.

3. **Message Ordering in Kafka**: Ensure that Kafka events are processed in the correct order, based on accreditationId, to maintain consistency across distributed systems.

4. **Pessimistic Locking (if necessary)**: In scenarios with high contention, pessimistic locking can be used to lock the accreditation record during the update process, preventing concurrent modifications.

## Scaling the System for Increased Traffic

To handle an increase in client-facing traffic and ensure the scalability of the service, the following strategies should be implemented:

1. **Horizontal Scaling**: 
   - Deploy multiple instances of the Spring Boot application behind a load balancer (e.g., AWS ELB). This ensures traffic is distributed evenly across instances, reducing the risk of bottlenecks.

2. **Database Optimization**: 
   - Implement connection pooling (e.g., HikariCP) to manage database connections efficiently.
   - Add indexes to frequently queried fields like `user_id` and `accreditation_id` to improve query performance.

3. **Caching**: 
   - Use a caching layer like Redis or Memcached to store frequently accessed accreditation data, reducing the load on the database and improving response times.

4. **API Rate Limiting**: 
   - Implement rate limiting for client-facing APIs using tools like AWS API Gateway or NGINX to prevent abuse and manage traffic efficiently.

5. **Asynchronous Processing**: 
   - For long-running tasks, offload them to an asynchronous job queue using Kafka or AWS SQS. This ensures that API responses remain fast even under heavy load.

6. **Auto-Scaling**: 
   - Use auto-scaling policies to automatically add or remove instances of the application based on real-time traffic patterns. This ensures the service can handle traffic spikes efficiently.

By applying these strategies, the accreditation service can handle increased traffic and maintain high availability without affecting the user experience.

## Running the Service
**Prerequisites**
- Docker
- Docker Compose

**How to run the application:**
1. Clone the repository:

   ```bash
   git clone <repository-url>
   cd accreditation-service

2. Build the Docker images and run the services:

   ```bash
   ./run.sh

3. Access the service:

   The application will be available on [http://localhost:9999](http://localhost:9999).

## Endpoints

**Admin API:**
- `POST /user/accreditation`: Creates a new accreditation for a user.
- `PUT /user/accreditation/{accreditationId}`: Updates the status of an accreditation.
- `GET /user/{userId}/accreditation`: Retrieves all accreditations for a user.
