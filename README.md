# RSEM – Remote Desktop Environment Manager

A role-based web app for keeping a support team's project resources in one place. Each project holds its own **links** (portals, consoles, tools) and **SOPs** (written steps or uploaded PDF documents). Admins manage projects and users and decide who can see which project. Regular users see only the projects they're assigned to.

Built with **Java 21, Spring Boot 4, Spring Security, Spring Data JPA, Thymeleaf and MySQL**.

---

## Features

- **Login and roles**: form login with Spring Security, BCrypt password hashing, and `ADMIN` / `USER` roles
- **Project access control**: admins see every project; users see only the projects assigned to them
- **Project management** (admin): create, edit, delete and search projects
- **Items per project**: add, edit, delete and search items inside a project
  - `LINK`: a named URL
  - `SOP`: text content, with an optional uploaded file (PDF etc., up to 10 MB)
- **User management** (admin): create, edit, enable/disable and delete users, and assign them to projects
- **Method-level security**: `@PreAuthorize` on admin-only actions, plus URL rules for `/admin/**`

## Tech stack

| Layer | Technology |
|---|---|
| Language | Java 21 |
| Framework | Spring Boot 4.0 (Web MVC) |
| Security | Spring Security 6, BCrypt |
| Persistence | Spring Data JPA / Hibernate, MySQL |
| Views | Thymeleaf, HTML, CSS |
| Build | Maven |

## Project structure

```
envmanager/
└── src/main/java/com/remotemanager/envmanager/
    ├── config/        SecurityConfig, WebConfig (serves /uploads), DataInitializer (seed users)
    ├── controller/    ProjectController, ItemController, AdminUserController
    ├── model/         User, Project, Item, ItemType
    ├── repository/    Spring Data JPA repositories
    └── service/       Business logic + CustomUserDetailsService
└── src/main/resources/
    ├── templates/     Thymeleaf pages (login, projects, items, users)
    └── static/        CSS and images
```

### Data model

- **User** ↔ **Project**: many-to-many (`user_projects` join table)
- **Project** → **Item**: one-to-many (items are deleted with their project)

## Running locally

**Prerequisites:** JDK 21, Maven, MySQL 8

1. Create the database:
   ```sql
   CREATE DATABASE rsem_db;
   ```
2. Set your MySQL username and password in `envmanager/src/main/resources/application.properties`:
   ```properties
   spring.datasource.url=jdbc:mysql://localhost:3306/rsem_db
   spring.datasource.username=<your-username>
   spring.datasource.password=<your-password>
   ```
   Tables are created automatically on first run (`ddl-auto=update`).
3. Start the app:
   ```bash
   cd envmanager
   mvn spring-boot:run
   ```
4. Open http://localhost:8080 and log in.

### Demo accounts

On startup the app creates two users if they don't already exist:

| Username | Password | Role |
|---|---|---|
| `admin` | `admin123` | ADMIN |
| `testuser1` | `testuser@123` | USER |

Change these before using the app anywhere other than your own machine.

## Routes

| Path | Who | Purpose |
|---|---|---|
| `/login` | Everyone | Login page |
| `/projects` | Logged-in users | Project list (filtered by role) |
| `/projects/new`, `/projects/save`, `/projects/delete/{id}` | Admin | Manage projects |
| `/projects/{projectId}/items` | Logged-in users | Links and SOPs for a project |
| `/projects/{projectId}/items/search?keyword=` | Logged-in users | Search items |
| `/admin/users` | Admin | Manage users and project assignments |

## Planned improvements

- Move database credentials to environment variables
- Store uploaded SOP files in cloud storage instead of the local `uploads/` folder
- Switch delete actions from GET links to POST requests
- Unit and integration tests

## Author

**Seetharaman Kumar Iyer**, [GitHub @Benex1114](https://github.com/Benex1114)
