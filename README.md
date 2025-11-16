# Twitter-like API 𝕏

A learning-oriented REST API inspired by Twitter/X. It focuses on core social features (users and tweets) built with modern Spring Boot, JWT-based authentication, and MySQL persistence.

## Features

- User registration and login with JWT authentication
- Role-based access control with method-level security (ADMIN and USER)
- Users: create (admin), read, update, delete with ownership/admin rules
- Tweets: create, list (public), get by id (public), update/delete by owner
- Data validation on all input DTOs
- MySQL persistence via Spring Data JPA (DDL auto-update for local dev)

Planned:

- Endpoints for likes, comments, and following/followers
- Feed/timeline endpoints
- OpenAPI/Swagger docs (springdoc)

## Tech Stack

- Java 21, Maven
- Spring Boot 3.5 (Web, Validation, Security, Data JPA)
- JWT (com.auth0:java-jwt)
- MySQL (runtime)
- Lombok (dev convenience)

## Architecture

- Layered structure: controller → service → repository → entity
- DTOs and mappers to isolate transport models from persistence models
- Stateless security with a custom JWT filter and method-level authorization

```
src/main/java/dev/tuchanski/api
├── config/security      # Security configuration and filter chain
├── controller           # REST controllers (auth, users, tweets)
├── dto                  # Request/response DTOs
├── entity               # JPA entities (User, Tweet, Like, Comment, Follow)
├── infra/security       # JWT filter and helpers
├── mapper               # Mapping between entities and DTOs
├── repository           # Spring Data repositories
└── service              # Business logic and token service
```

## Configuration

Default local configuration is set in `src/main/resources/application.properties`:

- Server port: 8080 (default)
- Database: `jdbc:mysql://localhost:3307/twitter` (user: `root`, password: `secret`)
- Hibernate: `spring.jpa.hibernate.ddl-auto=update` (auto-create/alter tables for dev)
- JWT secret: `api.security.token.secret`

## Getting Started

Prerequisites:

- Java 21
- Maven 3.9+
- MySQL running locally (adjust `application.properties` if needed)

Run locally:

```bash
./mvnw spring-boot:run
```

The API will be available at http://localhost:8080.

Tip: enable annotation processing for Lombok in your IDE.

## API at a Glance

Base URL: `/api`

Authentication

- POST `/auth/register` — create a new user (public)
- POST `/auth/login` — obtain a JWT token (public)

Users (secured; method-level rules)

- POST `/users` — create user (ADMIN)
- GET `/users` — list all users (ADMIN)
- GET `/users/{id}` — get user by id (ADMIN or the same user)
- GET `/users/username/{username}` — get by username (ADMIN or the same user)
- PATCH `/users/{id}` — update (ADMIN or the same user)
- DELETE `/users/{id}` — delete (ADMIN or the same user)

Tweets

- GET `/tweets` — list tweets (public). Optional `?username=john` filters by author
- GET `/tweets/{id}` — get tweet by id (public)
- POST `/tweets` — create tweet (requires Bearer token)
- PUT `/tweets/{id}` — update tweet (requires Bearer token; owner enforced by service)
- DELETE `/tweets/{id}` — delete tweet (requires Bearer token; owner enforced by service)

## Authentication & Authorization

- Login returns a JWT. Send it on subsequent requests using the `Authorization` header:
  `Authorization: Bearer <token>`
- Security is stateless; the custom filter validates the token and sets the security context
- Method-level annotations (e.g., `@PreAuthorize`) enforce ownership/role rules

## Example Requests

Register a user:

```bash
curl -X POST http://localhost:8080/api/auth/register \
  -H "Content-Type: application/json" \
  -d '{
    "name": "Jane Doe",
    "username": "janed",
    "email": "jane@example.com",
    "password": "pass1234"
  }'
```

Login:

```bash
curl -X POST http://localhost:8080/api/auth/login \
  -H "Content-Type: application/json" \
  -d '{
    "username": "janed",
    "password": "pass1234"
  }'
```

Response (200):

```json
{
  "token": "<jwt>",
  "id": "<uuid>",
  "username": "janed"
}
```

Create a tweet:

```bash
curl -X POST http://localhost:8080/api/tweets \
  -H "Content-Type: application/json" \
  -H "Authorization: Bearer <jwt>" \
  -d '{ "content": "Hello, world!" }'
```

List tweets (all or by username):

```bash
curl "http://localhost:8080/api/tweets"
# or
curl "http://localhost:8080/api/tweets?username=janed"
```

## Data Model (simplified)

- User (id, name, username, email, password, bio, role, timestamps)
- Tweet (id, content, author, timestamps)
- Like (user ↔ tweet)
- Comment (user ↔ tweet, content, timestamps)
- Follow (follower ↔ followed, timestamp)

## Roadmap

- [ ] Public endpoints for user profiles
- [ ] Likes, comments, and follow management endpoints
- [ ] Feed/timeline by followed users
- [ ] Pagination and sorting
- [ ] OpenAPI/Swagger UI
- [ ] Test coverage (unit and integration)

## Notes

- This project is for educational purposes.
- Use strong secrets in production and disable DDL auto-update.
- Consider Docker for local MySQL if you don’t have it installed.
