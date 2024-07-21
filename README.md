<h1 align="center">
   Ktor: A DDD Example Project
</h1>

---

Welcome to the Ktor DDD Example API, a Domain-Driven Design (DDD) example project using Ktor and best practices!

## 🌟 Overview

This project demonstrates the implementation of a simple blog post API using Domain-Driven Design principles and Ktor
framework. It showcases best practices in Kotlin backend development, including clean architecture, proper separation of
concerns, and robust error handling.

## 🎯 Features

- ✅ Create, read, update, and delete blog posts
- 👥 User authentication and authorization
- 🔍 Filter posts by user
- ✨ Input validation
- 🔒 Secure API endpoints

## 💻 Tech Stack

- 🗄️ Ktor: Lightweight, flexible web framework for Kotlin
- 🔐 Authentication: JWT-based authentication with Keycloak
- 📊 Serialization: Kotlinx Serialization
- 💉 Dependency Injection: Koin
- 🗃️ Database: PostgreSQL
- 🔗 ORM: Exposed (DAO)
- 🧪 Testing: JUnit

## 📁 Project Structure

The project follows a DDD-inspired structure:

```
src
├── main
│   ├── kotlin
│   │   └── com
│   │       └── example
│   │           ├── Application.kt
│   │           ├── configuration
│   │           │   ├── Database.kt
│   │           │   ├── HTTP.kt
│   │           │   ├── Koin.kt
│   │           │   ├── Monitoring.kt
│   │           │   ├── Routing.kt
│   │           │   ├── Security.kt
│   │           │   ├── Serialization.kt
│   │           │   └── Validation.kt
│   │           ├── exceptions
│   │           │   ├── EnvironmentException.kt
│   │           │   └── ForbiddenException.kt
│   │           ├── post
│   │           │   ├── PostEntity.kt
│   │           │   ├── PostRoutes.kt
│   │           │   ├── dto
│   │           │   │   ├── PostRq.kt
│   │           │   │   └── PostRs.kt
│   │           │   ├── repository
│   │           │   │   ├── PostDaoRepository.kt
│   │           │   │   └── PostRepository.kt
│   │           │   ├── schema
│   │           │   │   ├── PostDao.kt
│   │           │   │   └── PostTable.kt
│   │           │   └── usecase
│   │           │       ├── PostService.kt
│   │           │       └── PostServiceImpl.kt
│   │           ├── shared
│   │           │   ├── IdEntity.kt
│   │           │   ├── dto
│   │           │   │   └── Validatable.kt
│   │           │   └── repository
│   │           │       ├── EntityRepository.kt
│   │           │       └── IdEntityRepository.kt
│   │           └── utils
│   │               ├── Aliases.kt
│   │               ├── Environment.kt
│   │               ├── ErrorMessages.kt
│   │               ├── ErrorResponse.kt
│   │               ├── HelperFunctions.kt
│   │               └── UUIDSerializer.kt
│   └── resources
│       ├── logback.xml
│       └── openapi
│           └── documentation.yaml
└── test
    └── kotlin
        └── com
            └── example
                ├── ApplicationTest.kt
                ├── configuration
                │   ├── TestKoin.kt
                │   └── TestSecurity.kt
                ├── post
                │   ├── PostRoutesTest.kt
                │   └── repository
                │       └── PostInMemoryRepository.kt
                ├── shared
                │   └── repository
                │       └── InMemoryRepository.kt
                └── utils
                    └── HttpClient.kt
```

## 🚀 Getting Started

1. Clone the repository:
   ```
   git clone https://github.com/MrKekovich/ktor-example-ddd
   ```

2. Navigate to the project directory:
   ```
   cd ktor-example-ddd
   ```

3. Build the project:
   ```
   ./gradlew build
   ```

4. Run the application:
   ```
   ./gradlew run
   ```

The API will be available at `http://localhost:8080`.

## 📖 API Documentation

For detailed API documentation, please refer to
the [OpenAPI specification](./src/main/resources/openapi/documentation.yaml).
