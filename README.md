# Student Course Registration System

A full stack Java web application where students register for courses and an admin manages courses and students.
Built with **Spring Boot, Spring Security, Spring Data JPA, PostgreSQL** and an HTML/CSS/JavaScript frontend.

## Features
- Login with **Spring Security** (form login, BCrypt-hashed passwords, roles `ADMIN` and `STUDENT`)
- Student sign up, then register for and drop courses
- Rules enforced by the server: no duplicate registration, no registration for full courses
- Admin: add, edit and delete courses, and view all students
- **Pagination and sorting** on courses and students, plus search by code or title
- Validation with clear errors and proper HTTP status codes (400, 401, 403, 404, 409)

## Tech stack
| Layer | Technology |
|-------|------------|
| Backend | Java 17, Spring Boot 3, Spring Web, Spring Security, Spring Data JPA, Bean Validation |
| Database | PostgreSQL |
| Frontend | HTML, CSS, JavaScript (fetch API) |
| Build tool | Maven |

## Project structure
```
src/main/java/com/ditisha/scrs
├── config       SecurityConfig, DataInitializer (demo admin + sample courses)
├── controller   REST controllers
├── service      Business logic
├── repository   Spring Data JPA repositories
├── model        Entities: AppUser, Student, Course, Enrollment
├── dto          Request and response records
└── exception    Global exception handler
src/main/resources/static   login.html, register.html, index.html, css/
```

## How to run
**Prerequisites:** JDK 17+, PostgreSQL

1. Create the database: `CREATE DATABASE course_db;`
2. Set your database login as environment variables (or edit `application.properties` locally):
   ```
   DB_USERNAME=postgres
   DB_PASSWORD=your_password
   ```
3. Run `CourseRegistrationApplication` from your IDE, or `mvn spring-boot:run`.
4. Open **http://localhost:8081**

Tables are created automatically on first run.

**Demo admin login:** `admin` / `Admin@123` (created on first run, for demo only; change it for any real use).
Students create their own account from the sign up page.

## REST API
| Method | Endpoint | Access | Description |
|--------|----------|--------|-------------|
| POST | `/api/auth/register` | Public | Student sign up |
| GET | `/api/me` | Logged in | Current user |
| GET | `/api/courses?q=&page=&size=&sortBy=&direction=` | Logged in | Paged, sorted, searchable courses |
| POST / PUT / DELETE | `/api/courses`, `/api/courses/{id}` | Admin | Manage courses |
| GET | `/api/students?page=&size=` | Admin | Paged student list |
| GET | `/api/enrollments/my` | Student | My courses |
| POST / DELETE | `/api/enrollments/{courseId}` | Student | Register / drop |

## Screenshots
_Add screenshots here._

## Future improvements
- JWT authentication
- Unit and integration tests
- CSRF protection and password change
- Docker setup and deployment

## Author
Ditisha Mohite, BE Information Technology
