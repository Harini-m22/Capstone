Online Bus Booking System - Documentation
1. Introduction
This document provides a comprehensive guide on setting up, configuring, and running the Online Bus Booking System. It includes details on API endpoints, request/response formats, and data validation rules.
2. Project Setup
### Prerequisites
Ensure you have the following installed:
- Java 21 or later
- Maven
- MySQL or any preferred database
### Steps to Run the Application
1. Clone the repository: `git clone <repo_url>`
2. Navigate to the project directory: `cd demoBus`
3. Build the project: `mvn clean install`
4. Configure database settings in `application.properties`
5. Run the application: `mvn spring-boot:run`
3. Configuration Details
### Database Configuration
The application uses MySQL as the primary database. Update the `application.properties` file:

spring.datasource.url=jdbc:mysql://localhost:3306/demoBus
spring.datasource.username=root
spring.datasource.password=root
spring.jpa.hibernate.ddl-auto=update
