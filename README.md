# Job Application Tracker

A REST API for tracking job applications built with Spring Boot.

## Features
- JWT authentication — register and login to manage your own applications
- Full CRUD — add, update, delete and filter job applications
- Filter by status — APPLIED, INTERVIEW, OFFER, REJECTED
- Each user only sees their own applications
- Input validation and global exception handling

## Tech Stack
- Java 17 + Spring Boot 4
- Spring Security + JWT
- MySQL + Hibernate JPA
- Docker + Docker Compose
- Unit tests with JUnit 5 + Mockito
- CI/CD with GitHub Actions
- API documentation with Swagger

## How to Run
```bash
docker compose up
```
Then open http://localhost:8080/swagger-ui/index.html

## API Endpoints
- POST /auth/register — create account
- POST /auth/login — get JWT token
- GET /jobApplications — get your applications
- POST /jobApplications — add new application
- PUT /jobApplications/{id} — update application
- DELETE /jobApplications/{id} — delete application
