# Finance Budgeting Application

A Spring Boot REST API for personal finance management. The application supports user authentication, wallet management, categories, transactions, and ledger entries so users can organize income/expense activity in a structured way.

## Tech Stack

- Java 21
- Spring Boot 4
- Spring Web MVC
- Spring Data JPA (Hibernate)
- Spring Security + JWT
- MySQL
- Lombok
- ModelMapper
- Maven Wrapper (`./mvnw`)

## Core Domain

The API models the following entities:

- **User**: profile, credentials, roles, and ownership of wallets/categories.
- **Wallet**: account-like container with balance and account number.
- **Category**: user-defined classification for financial entries.
- **Transaction**: aggregate business transaction with total amount and linked ledger entries.
- **LedgerEntry**: debit/credit line item tied to wallet, category, and transaction.
- **Role**: authorization roles for users.

`TransactionType` values are:

- `DEBIT`
- `CREDIT`

## Project Structure

```text
src/main/java/com/Finance/demo
├── Config
├── Controller
├── DTO
├── Enums
├── Exceptions
├── Model
├── Repository
├── Request
├── Response
├── Security
└── Services
```

## Configuration

Default configuration is in `src/main/resources/application.properties`.

Important properties:

- `server.port=9193`
- `api.prefix=/api/v1`
- MySQL datasource URL/user/password
- `spring.jpa.hibernate.ddl-auto=update`

> ⚠️ The repository currently contains concrete DB credentials in `application.properties`. For real deployments, move secrets to environment variables or a secure secret manager.

## Prerequisites

- Java 21 installed
- MySQL running locally
- A MySQL database named `Finance_Budgeting`

Example SQL:

```sql
CREATE DATABASE Finance_Budgeting;
```

## Running the Application

From the repository root:

```bash
./mvnw spring-boot:run
```

The API starts on:

```text
http://localhost:9193
```

## Authentication Flow (JWT)

Public endpoints:

- `POST /api/v1/auth/register`
- `POST /api/v1/auth/login`

All other endpoints require a valid Bearer token.

### Register example

```bash
curl -X POST http://localhost:9193/api/v1/auth/register \
  -H "Content-Type: application/json" \
  -d '{
    "firstname": "Jane",
    "lastname": "Doe",
    "email": "jane@example.com",
    "password": "StrongPassword123"
  }'
```

### Login example

```bash
curl -X POST http://localhost:9193/api/v1/auth/login \
  -H "Content-Type: application/json" \
  -d '{
    "email": "jane@example.com",
    "password": "StrongPassword123"
  }'
```

Response includes:

```json
{ "token": "<jwt_token>" }
```

Use the token in subsequent requests:

```bash
-H "Authorization: Bearer <jwt_token>"
```

## API Overview

Base prefix for secured endpoints: `/api/v1`

### Users (`/api/v1/users`)

- `GET /{id}`
- `GET /by-email?email=...`
- `POST /add`
- `POST /update/{Id}`
- `DELETE /delete/{Id}`
- `GET /all/wallets?Id=...`
- `GET /all/users`

### Wallets (`/api/v1/wallet`)

- `GET /id/{Id}`
- `GET /{accountNumber}`
- `GET /balance/{Id}`
- `GET /User/wallet/{Id}`
- `POST /create`
- `PUT /update/{Id}`
- `DELETE /delete/{Id}`

### Categories (`/api/v1/category`)

- `GET /get/{Id}`
- `GET /get/name/{userId}?name=...`
- `POST /create`
- `POST /update/{Id}`
- `DELETE /delete/{Id}`
- `GET /all/{userId}`

### Transactions (`/api/v1/transaction`)

- `POST /create/{userId}`
- `GET /get/{Id}`
- `GET /get/all/{userId}`
- `POST /update/{Id}`
- `DELETE /delete/{Id}`
- `GET /get/users/{Id}`

### Ledger Entries (`/api/v1/ledgerEntry`)

- `GET /ledger/{Id}`
- `GET /amount/{Id}`
- `GET /get/wallet/{Id}`
- `GET /transaction/{Id}`
- `GET /category/{Id}`
- `GET /entries/{walletId}`

## Running Tests

```bash
./mvnw test
```

## Notes & Recommendations

- Consider adding API documentation (Swagger/OpenAPI).
- Consider adding Docker support for app + MySQL.
- Consider moving sensitive configuration to environment variables.
- Add validation annotations on request DTOs for input safety.

---

If you want, I can also add:

1. a `.env`-style configuration approach,
2. a `docker-compose.yml` for local setup,
3. and a starter OpenAPI/Swagger config.
