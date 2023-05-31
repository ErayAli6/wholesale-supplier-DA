# Wholesale Supplier Application

The Wholesale Supplier Application is a web-based application that allows wholesale suppliers to manage their products, companies, and purchases. The application is built using Java and Spring Boot framework.

## Getting Started

To get started with the application, you need to have Java 17 and Maven installed on your system. You can download Java from the official website and install it on your system. To install Maven, you can follow the instructions provided on the Apache Maven website.

Once you have Java and Maven installed, you can clone the repository and build the application using the following commands:
```
git clone https://github.com/<username>/wholesale-supplier.git
cd wholesale-supplier
mvn clean install
```
This will build the application and create a JAR file in the `target` directories.

## Running the Application

To run the back-end application, you can use the following command:
```
java -jar target/back-end-0.0.1-SNAPSHOT.jar
```

This will start the back-end application on port 8080. You can access the Swagger documentation for the back-end API by navigating to `http://localhost:8080/swagger-ui.html` in your web browser.

To run the front-end application, you can use the following command:
```
java -jar target/front-end-0.0.1-SNAPSHOT.jar
```
This will start the front-end application on port 8081. You can access the front-end application by navigating to `http://localhost:8081` in your web browser.

## Features

The Wholesale Supplier Application provides the following features:

- Manage companies: Add, update, and delete companies.
- Manage products: Add, update, and delete products.
- Manage purchases: Create and manage puchases for companies.
- Authentication and authorization: Secure the application using Spring Security and JWT authentication.

## Default Admin User

The default admin user for the application is `admin` with password `admin`. You can use this user to log in to the application and manage the users.

## Technologies Used

The Wholesale Supplier Application is built using the following technologies:

- Java
- Spring Boot
- Spring Data JPA
- Spring Security
- JWT
- Maven
- H2 Database
- OpenApi Swagger
