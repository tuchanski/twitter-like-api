# Twitter-like API (𝕏 Inspired)

An educational, production-style REST API inspired by Twitter/X. It implements core social features (users, tweets, likes, comments, follows) using modern Spring Boot, stateless JWT authentication, and MySQL persistence.

## 1. Overview

This project serves as a learning platform showcasing clean layering, DTO mapping, role-based + ownership security, and comprehensive OpenAPI documentation suitable for extension into a full social feed system.

## 2. Features

Implemented:

- User registration & login (JWT bearer)
- Granting admin role
- User CRUD with admin/ownership rules
- Tweets: create, list (public), filter by author, retrieve, update, delete
- Likes: like/unlike tweets, list likes by tweet or user
- Comments: create, list by tweet, retrieve, update, delete
- Follow system: follow/unfollow users, list followers & following
- Centralized exception handling with structured error response
- Input validation on request DTOs
- OpenAPI configuration + Swagger UI redirect (`/docs`)

Security Highlights:

- Stateless JWT filter
- Method-level authorization (`@PreAuthorize`) for sensitive operations
- Granular role + principal-based access (admin vs owner)

## 3. Tech Stack

- Java 21
- Spring Boot 3.5 (Web, Validation, Security, Data JPA)
- Springdoc OpenAPI 3 (Swagger UI)
- JWT (Auth0 `java-jwt`)
- MySQL (development persistence)
- Lombok (boilerplate reduction)
- Maven (build + dependency management)

## 4. Architecture

Layered structure emphasizing separation of concerns:

- `controller` → HTTP / input boundary
- `service` → business logic & security checks (ownership, role checks)
- `repository` → persistence via Spring Data JPA
- `entity` → JPA mapped domain models
- `dto` + `mapper` → transport and transformation layers
- `infra/security` → JWT parsing & authentication filter
- `exception` → domain-specific and validation exception handling

```
src/main/java/dev/tuchanski/api
├── config              # OpenAPI, Web, Security configuration
├── controller          # REST controllers (auth, users, tweets, likes, comments, follows)
├── dto                 # Request/response DTOs per feature
├── entity              # JPA entities (User, Tweet, Like, Comment, Follow)
├── exception           # Custom exception hierarchy & handler
├── infra/security      # JWT filter & helper classes
├── mapper              # Entity <-> DTO conversion logic
├── repository          # Spring Data JPA repositories
└── service             # Business logic & token services
```

## 5. Configuration

Default settings in `src/main/resources/application.properties`:

- Server port: `8080`
- MySQL URL: `jdbc:mysql://localhost:3307/twitter`
- MySQL credentials: `root` / `secret` (change for real use)
- Hibernate DDL: `spring.jpa.hibernate.ddl-auto=update` (dev only)
- JWT secret property: `api.security.token.secret`

Override by editing the properties file or providing environment variables / JVM system properties at runtime.

## 6. Running the Project

Prerequisites:

- Java 21 installed
- Maven 3.9+ (or use wrapper)
- Local MySQL instance (adjust URL/credentials if different)

Windows (PowerShell):

```
./mvnw.cmd clean spring-boot:run
```

Unix/macOS:

```
./mvnw clean spring-boot:run
```

After startup the API is available at: `http://localhost:8080`

Swagger UI: `http://localhost:8080/docs` (redirects to `/swagger-ui/index.html`)

## 7. Authentication & Security

- Obtain a JWT via `POST /api/auth/login`.
- Send token on protected requests: `Authorization: Bearer <token>`.
- Public endpoints: login/register, listing & retrieving tweets, reading comments, reading users (GET), OpenAPI docs.
- Protected endpoints enforce roles or ownership via `@PreAuthorize` and service-layer checks.
- Passwords stored using BCrypt.

## 8. API Endpoints

Base path prefix: `/api`

### Authentication

| Method | Path             | Auth   | Description                  |
| ------ | ---------------- | ------ | ---------------------------- |
| POST   | `/auth/register` | Public | Register a new user          |
| POST   | `/auth/login`    | Public | Authenticate and receive JWT |

### Users

| Method | Path                            | Auth           | Description          |
| ------ | ------------------------------- | -------------- | -------------------- |
| POST   | `/users`                        | Admin          | Create user          |
| GET    | `/users`                        | Admin          | List all users       |
| GET    | `/users/{id}`                   | Admin or Owner | Get user by UUID     |
| GET    | `/users/username/{username}`    | Admin or Owner | Get user by username |
| PATCH  | `/users/{id}`                   | Admin or Owner | Partial update       |
| DELETE | `/users/{id}`                   | Admin or Owner | Delete user          |
| PATCH  | `/users/{targetUsername}/admin` | Admin          | Grant admin role     |

### Tweets

| Method | Path           | Auth           | Description                                    |
| ------ | -------------- | -------------- | ---------------------------------------------- |
| GET    | `/tweets`      | Public         | List all tweets (optional `?username=` filter) |
| GET    | `/tweets/{id}` | Public         | Get tweet by UUID                              |
| POST   | `/tweets`      | Bearer         | Create tweet                                   |
| PUT    | `/tweets/{id}` | Bearer (Owner) | Update tweet content                           |
| DELETE | `/tweets/{id}` | Bearer (Owner) | Delete tweet                                   |

### Likes

| Method | Path                      | Auth           | Description                |
| ------ | ------------------------- | -------------- | -------------------------- |
| POST   | `/tweets/{tweetId}/likes` | Bearer         | Like a tweet               |
| GET    | `/likes/{likeId}`         | Public         | Get like by UUID           |
| GET    | `/tweets/{tweetId}/likes` | Public         | List likes for tweet       |
| GET    | `/users/{username}/likes` | Public         | List likes by user         |
| DELETE | `/tweets/{tweetId}/likes` | Bearer (Owner) | Remove like (current user) |

### Comments

| Method | Path                         | Auth           | Description                                 |
| ------ | ---------------------------- | -------------- | ------------------------------------------- |
| POST   | `/tweets/{tweetId}/comments` | Bearer         | Create comment on tweet                     |
| GET    | `/comments/{id}`             | Public         | Retrieve comment by UUID                    |
| GET    | `/tweets/{tweetId}/comments` | Public         | List comments of tweet (desc creation time) |
| PUT    | `/comments/{id}`             | Bearer (Owner) | Update comment                              |
| DELETE | `/comments/{id}`             | Bearer (Owner) | Delete comment                              |

### Follows

| Method | Path                                     | Auth   | Description                     |
| ------ | ---------------------------------------- | ------ | ------------------------------- |
| POST   | `/users/{usernameFollowTarget}/follow`   | Bearer | Follow a user                   |
| GET    | `/users/{username}/following`            | Public | List users followed by username |
| GET    | `/users/{username}/followers`            | Public | List users following username   |
| DELETE | `/users/{usernameUnfollowTarget}/follow` | Bearer | Unfollow a user                 |

### Documentation

| Method | Path              | Auth   | Description            |
| ------ | ----------------- | ------ | ---------------------- |
| GET    | `/docs`           | Public | Redirect to Swagger UI |
| GET    | `/swagger-ui/**`  | Public | Swagger UI assets      |
| GET    | `/v3/api-docs/**` | Public | Raw OpenAPI JSON       |

## 9. Example Usage

Register:

```bash
curl -X POST http://localhost:8080/api/auth/register \
  -H "Content-Type: application/json" \
  -d '{"name":"Jane Doe","username":"janed","email":"jane@example.com","password":"pass1234"}'
```

Login:

```bash
curl -X POST http://localhost:8080/api/auth/login \
  -H "Content-Type: application/json" \
  -d '{"username":"janed","password":"pass1234"}'
```

Create tweet:

```bash
curl -X POST http://localhost:8080/api/tweets \
  -H "Content-Type: application/json" \
  -H "Authorization: Bearer <jwt>" \
  -d '{"content":"Hello, world!"}'
```

Like tweet:

```bash
curl -X POST http://localhost:8080/api/tweets/<tweet-uuid>/likes \
  -H "Authorization: Bearer <jwt>"
```

Comment on tweet:

```bash
curl -X POST http://localhost:8080/api/tweets/<tweet-uuid>/comments \
  -H "Content-Type: application/json" \
  -H "Authorization: Bearer <jwt>" \
  -d '{"content":"Nice post!"}'
```

Follow user:

```bash
curl -X POST http://localhost:8080/api/users/otheruser/follow \
  -H "Authorization: Bearer <jwt>"
```

## 10. OpenAPI / Swagger Docs

OpenAPI metadata is defined in `OpenApiConfig`. Access interactive docs at:

```
http://localhost:8080/docs
```

Raw spec (JSON):

```
http://localhost:8080/v3/api-docs
```

Use these endpoints for client generation or exploration.

Security scheme name: `bearerAuth` (standard HTTP Bearer JWT).

## 11. Data Model (Simplified)

- User: id (UUID), name, username, email, password (BCrypt), bio, roles, timestamps
- Tweet: id (UUID), content, author (User), timestamps
- Like: id (UUID), user, tweet, timestamps
- Comment: id (UUID), user, tweet, content, timestamps
- Follow: id (UUID), follower (User), followed (User), timestamp


## 12. Contributing

Contributions welcome! Feel free to open issues or PRs for improvements, refactors, or new features. Please keep code style consistent and ensure new endpoints are documented with OpenAPI annotations.

## 15. License & Disclaimer

Licensed under the MIT License. This codebase is for educational purposes; do not deploy as-is to production without hardening (secrets management, proper CORS, rate limiting, logging, monitoring, and stricter security settings).

