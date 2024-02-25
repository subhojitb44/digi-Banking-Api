# OAuth Service - Authentication Project

OAuth Service is an authentication project built using Java 17 and Spring Boot 3.1.1 with MongoDB as the database
backend.

## Overview

This project provides authentication services using OAuth (Open Authorization) for securing access to resources. It
implements various authentication mechanisms and provides endpoints for user authentication, authorization, and token
management.

## Features

- User registration and authentication
- Role-based access control
- JWT (JSON Web Token) based authentication
- Session management
- Token-based authentication and authorization
- Integration with MongoDB for data storage

## Technologies Used

- Java 17
- Spring Boot 3.1.1
- Spring Security
- MongoDB
- Maven (for dependency management)
- JSON Web Tokens (JWT) for authentication

## Getting Started

To run the project locally, follow these steps:

1. Clone the repository:

```bash
git clone https://github.com/subhojitb44/com.authentication.git
cd com.authentication
```

2. Configure MongoDB:

    - Ensure MongoDB is installed and running locally or update the MongoDB connection settings in
      the `application.properties` file.

3. Build and run the project:

```bash
mvn spring-boot:run
```

4. Access the application:

   The application will be running at `http://localhost:8086`.

## Usage

- Register a new user: `/api/authenticate/register`
- Login: `/api/authenticate/login`
- Refresh/Validate Access Token: `/api/authenticate/validate-token`
- Logout: `/api/authenticate/logout`

## Contact

- [Subhojit Bhattacharya] - [subhojitb44@gmail.com]


