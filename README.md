# Hotel RESTful API

RESTful API for managing hotel properties, searching by various criteria, and gathering statistical data (histograms). 

## Tech Stack
* **Java 21**
* **Spring Boot 3.3.5** (Web, Data JPA, Validation)
* **Databases:** H2 (In-memory) / PostgreSQL (Production-ready)
* **Database Migrations:** Liquibase
* **Mapping & Boilerplate:** MapStruct, Lombok
* **Testing:** JUnit 5, Mockito, MockMvc
* **Documentation:** Swagger / OpenAPI 3

## Features
* **Multi-Database Support:** Configured Spring Profiles (`h2` and `postgresql`) to quickly switch between environments.
* **Performance Optimization:** Solved the **N+1 Select Problem** using `@EntityGraph` for fetching hotels with their amenities in a single SQL `LEFT JOIN` query.
* **Dynamic Queries & Aggregations:** Implemented **JPA Criteria API** for dynamic `GROUP BY` SQL query generation in the `/histogram` endpoint.
* **Clean Architecture:** Strict separation of concerns (Controller ➔ Service ➔ Repository) and isolated Data Transfer Objects (DTOs) mapped via **MapStruct**.
* **Robust Exception Handling:** Global `@RestControllerAdvice` intercepts validation errors, type mismatches, missing parameters, and database conflicts, returning standardized and readable JSON error responses.
* **Pre-populated Test Data:** Liquibase automatically initializes the database schema and injects diverse test data (hotels in Minsk and Moscow) on startup.

## Endpoints 
All endpoints are served under the `/property-view` context path.

* `GET /hotels` - Get a short list of all hotels.
* `GET /hotels/{id}` - Get detailed information about a specific hotel (including address, contacts, and amenities).
* `GET /search` - Search hotels by dynamic parameters (`name`, `brand`, `city`, `country`, `amenities`).
* `POST /hotels` - Create a new hotel property.
* `POST /hotels/{id}/amenities` - Add a list of amenities to an existing hotel.
* `GET /histogram/{param}` - Get grouped statistics (count of hotels) by a specific parameter (`brand`, `city`, `country`, `amenities`).

## How to Run

### Prerequisites
* Java 21 installed.
* Maven installed.

### 1. Running with H2 Database (Default)
By default, the application runs using an in-memory H2 database. Data is generated automatically via Liquibase.

```bash
mvn spring-boot:run
```
 * API Port: 8092
 * H2 Console: http://localhost:8092/property-view/h2-console
    * JDBC URL: jdbc:h2:mem:hoteldb, User: sa, Password: empty

### 2. Running with PostgreSQL

To run the application with a real database, ensure PostgreSQL is running (port 5432) and the database postgres and schema hotel_schema are created. Then run:

```bash 
mvn spring-boot:run -Dspring-boot.run.profiles=postgresql
```

## API Documentation (Swagger)
Once the application is running, you can explore the API, view schemas, and execute requests directly from your browser using Swagger UI:
* http://localhost:8092/property-view/swagger-ui/index.html

## Testing
The project is covered with Unit Tests (Service layer via Mockito) and Sliced WebMvc Tests (Controller layer via MockMvc). To execute them:

```bash 
mvn test
``` 
## Postman Collection

For manual API testing, a ready-to-use Postman collection is included in the project repository.
Open the postman folder in the project root.
Import the Hotel_API_Postman_Collection.json file into your Postman workspace.
