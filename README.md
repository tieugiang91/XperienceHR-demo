# XperienceHR Time Tracking Application

A Spring Boot application for tracking employee work hours with role-based access control and reporting capabilities.

## Technology Stack

- Java 17
- Spring Boot 3.2.0
- Spring Data JPA
- Spring Security
- PostgreSQL
- JSP with JSTL
- Flyway (Database Migration)
- MapStruct 1.5.5 (Object Mapping)
- Lombok (Boilerplate Reduction)
- Bootstrap 5
- Maven
- JaCoCo (Code Coverage)

## Architecture

This application follows the **Hexagonal Architecture** (Ports and Adapters) pattern, which provides:

- Clear separation of concerns
- Independence from frameworks and external systems
- Easy testability
- Flexibility to change adapters without affecting business logic

### Project Structure

```
src/main/java/com/xperiencehr/timetracking/
├── domain/                          # Core business logic (framework-independent)
│   ├── model/                       # Domain entities
│   └── port/                        # Interfaces (ports)
├── application/                     # Application services (use cases)
│   └── service/
├── adapter/                         # Adapters (implementations)
│   ├── persistence/                 # Database adapter
│   │   ├── entity/                  # JPA entities
│   │   ├── mapper/                  # MapStruct entity mappers
│   │   └── repository/              # JPA repositories
│   ├── web/                         # Web adapter
│   │   ├── controller/              # REST/MVC controllers
│   │   ├── dto/                     # Data Transfer Objects
│   │   ├── mapper/                  # MapStruct DTO mappers
│   │   ├── interceptor/             # Request interceptors
│   │   ├── exception/               # Exception handlers
│   │   └── config/                  # Web configuration
│   └── security/                    # Security adapter
└── TimeTrackingApplication.java     # Main application class
```

### Why Hexagonal Architecture?

1. **Business Logic Isolation**: Domain logic is completely independent of frameworks
2. **Testability**: Easy to test business logic without external dependencies
3. **Flexibility**: Can swap implementations (e.g., switch from PostgreSQL to MongoDB) without changing core logic
4. **Maintainability**: Clear boundaries make the codebase easier to understand and maintain
5. **Microservice Ready**: Well-suited for microservice architectures

### Object Mapping with MapStruct

The application uses **MapStruct** for type-safe object mapping between layers:

- **Entity Mappers** (`adapter.persistence.mapper`): Convert between JPA entities and domain models
- **DTO Mappers** (`adapter.web.mapper`): Convert between domain models and DTOs

**Key Benefits:**
- **Compile-time Safety**: Mapping errors caught at compile time, not runtime
- **Performance**: No reflection, generates plain Java code
- **Lombok Integration**: Works seamlessly with Lombok-generated getters/setters via `lombok-mapstruct-binding`
- **Null Safety**: Configured with `NullValuePropertyMappingStrategy.IGNORE` to prevent null value issues
- **Maintainability**: Interface-based mappers are easy to understand and maintain

## Database Setup

The application uses PostgreSQL with the following configuration:

- Database: `postgres`
- Username: `postgres`
- Password: `password`
- Port: `5432` (default)

### Schema

The database schema is managed by Flyway migrations located in `src/main/resources/db/migration/`:

- `V1__initial_schema.sql` - Creates tables and indexes
- `V2__sample_data.sql` - Inserts sample data
- `V3__generate_large_dataset.sql` - Seeds realistic load test data (100k time records)
- `V4__add_indexes.sql` - Adds optimized indexes for reporting queries

### Query Optimization

The original inefficient query used correlated subqueries in SELECT, GROUP BY, and ORDER BY clauses. Optimizations implemented:

1. **JOIN instead of subqueries**: Replaced correlated subqueries with proper JOINs
2. **Targeted Indexes**: Added indexes on `time_from`, `employee_id`, and `project_id`
3. **Comprehensive Dataset**: Generates 100k records to surface planner bottlenecks
4. **Explain Plan Monitoring**: Documented execution plans pre/post optimization (`EXPLANATION.md`)

These changes reduce query execution time significantly by:
- Eliminating repeated subquery execution
- Enabling index-based lookups
- Reducing table scans

## Features

### Security & Access Control

- **Role-based access control** with two roles:
  - `EMPLOYEE`: Can view only their own work hours
  - `ADMIN`: Can view all employees' work hours

- **In-memory authentication** with test accounts:
  - Tom (EMPLOYEE): username=`Tom`, password=`password`
  - Jerry (EMPLOYEE): username=`Jerry`, password=`password`
  - Admin (ADMIN): username=`admin`, password=`admin`

### Reporting

- Date range selection for custom reporting periods
- Default period: Last 1 month
- Displays employee name, project name, and total hours worked
- Pagination with page/size controls and total record counts
- Server-side validation of page/size parameters with sensible defaults
- Bootstrap-styled responsive UI

### Logging

Comprehensive logging at multiple levels:

- **HTTP Requests**: Start/end of every request with duration
- **SQL Queries**: All executed queries with execution time
- **Authentication Events**: Login success/failure, authorization denials
- **Exceptions**: All handled and unhandled exceptions

Log levels configured:
- Application: DEBUG
- Spring Web: DEBUG
- Spring Security: DEBUG
- Hibernate SQL: DEBUG

### Error Handling

- Centralized exception handling via `@ControllerAdvice`
- Graceful handling of missing static resources (e.g., favicon requests)
- User-friendly error pages
- No stack traces exposed to end users
- Proper HTTP status codes

## Building and Running

### Prerequisites

- JDK 17 or later
- Maven 3.6+
- PostgreSQL 12+

### Build

```bash
mvn clean package
```

### Run

```bash
mvn spring-boot:run
```

Or run the JAR directly:

```bash
java -jar target/timetracking-1.0.0.jar
```

The application will start on `http://localhost:8080`

### Tests and Coverage

Run the automated test suite:

```bash
mvn clean test
```

Generate a JaCoCo coverage report (outputs to `target/site/jacoco/index.html`):

```bash
mvn clean verify
```

## Usage

1. Navigate to `http://localhost:8080`
2. You'll be redirected to the login page
3. Login with one of the test accounts
4. Select a date range and (optionally) adjust the page size, then click "Generate" to view the report
5. Use the pagination controls to move through the dataset
6. Employees see only their records; admins see all records

## API Endpoints

- `GET /` - Redirects to report page
- `GET /login` - Login page
- `POST /login` - Process login
- `GET /report` - Work hours report (requires authentication)
- `POST /logout` - Logout

## Configuration

Key configuration in `application.properties`:

```properties
spring.datasource.url=jdbc:postgresql://localhost:5432/postgres
spring.jpa.hibernate.ddl-auto=validate
spring.flyway.enabled=true
spring.mvc.view.prefix=/WEB-INF/jsp/
logging.level.com.xperiencehr=DEBUG
```

## Design Decisions

1. **Hexagonal Architecture**: Chosen for maintainability and testability in a microservice context
2. **Flyway**: Ensures consistent database state across environments
3. **JPA over JdbcTemplate**: Provides object-relational mapping and reduces boilerplate
4. **In-memory Security**: Simplified for demo; production would use database or LDAP
5. **JSP with Bootstrap**: Modern, responsive UI without complex frontend frameworks
6. **Lombok**: Reduces boilerplate code for getters/setters/constructors

