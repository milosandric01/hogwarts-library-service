# 🧙‍♂️ Hogwarts Library Service

A backend system for managing a magical bookstore — built with **Spring Boot**, **MySQL**, **Flyway**, and **JOOQ**. The
service supports inventory management, dynamic pricing logic, and a loyalty system.

---

## 🛠 Tech Stack

- Java 17
- Spring Boot
- JOOQ
- Flyway
- MySQL
- Docker

---

## 🧑‍💻 Running the Service

### ✅ Prerequisites

- Java 17+
- Maven
- Docker (optional for containerized approach)

---

### ▶️ Running Locally (without Docker)

1. **Set up MySQL locally**

   Create a database called `hogwarts_library` and update your `application.yml`

   ```properties
   spring.datasource.username=root
   spring.datasource.password=your_password

2. **Build and run**

   Migrations and seeding will be triggered automatically in build time.

   ```properties
   mvn clean install
   java -jar target/hogwarts-library-0.0.1-SNAPSHOT.jar

### 🐳 Running with Docker

1. **Run using Docker Compose**

   ```bash
   docker-compose up -d
   ```

   This will spin up:
    - MySQL DB (with `hogwarts_library` schema)
    - Spring Boot app on `latest` version
    - Flyway migration
