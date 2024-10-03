# E-commerce Store Backend

This project is a backend implementation of an e-commerce store using Spring Boot and Java.

## Features

- Product management
- Shopping cart functionality
- Coupon system
- Checkout process
- Store statistics

## Project Structure

The project follows a standard Spring Boot structure:

- `model`: Contains entity classes (Product, Cart, CartItem, Coupon)
- `repository`: Interfaces for data access
- `service`: Business logic implementation
- `controller`: REST API endpoints
- `dto`: Data Transfer Objects

## Key Components

1. EcommerceService: Handles core business logic
2. EcommerceController: Exposes REST endpoints
3. Repositories: JPA repositories for data persistence

## API Endpoints

- POST `/api/ecommerce/cart/{cartId}/add`: Add a product to the cart
- POST `/api/ecommerce/cart/{cartId}/checkout`: Checkout process
- GET `/api/ecommerce/admin/statistics`: Retrieve store statistics

## Database

The project uses an H2 in-memory database for development purposes. The configuration can be found in the `application.properties` file.

## Running the Application

To run the application:

1. Ensure you have Java 21 and Maven installed
2. Navigate to the project root directory
3. Run `mvn spring-boot:run`

The application will start on `http://localhost:8080`

## Testing

You can run the tests using:

```
mvn test
```

## Dependencies

- Spring Boot
- Spring Data JPA
- H2 Database
- Jakarta Persistence API

For a complete list of dependencies, please refer to the `pom.xml` file.

## Configuration

Key configuration options can be found in the `application.properties` file.
