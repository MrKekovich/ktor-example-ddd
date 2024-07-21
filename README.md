<h1 align="center">Domain-Driven Design Example Project</h1>

<p align="center">
<a href="https://kotlinlang.org"><img src="https://img.shields.io/badge/kotlin-2.0.0-blue.svg?logo=kotlin" alt="Kotlin 2.0.0"></a>
<a href="https://ktor.io/"><img src="https://img.shields.io/badge/ktor-2.3.12-blue.svg?logo=ktor" alt="Ktor 2.3.12"></a>
<img src="https://img.shields.io/badge/version-1.0.0-g.svg" alt="">
</p>

---

**Welcome to the Ktor DDD Example API, a Domain-Driven Design (DDD) example project using Ktor and best practices!**

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

- 🗄️ Ktor 2.3.12: Lightweight, flexible web framework for Kotlin
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
│   │           ├── exceptions
│   │           ├── post
│   │           │   ├── dto
│   │           │   ├── repository
│   │           │   ├── schema
│   │           │   └── usecase
│   │           ├── shared
│   │           │   ├── dto
│   │           │   └── repository
│   │           └── utils
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
                ├── post
                ├── shared
                └── utils
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
