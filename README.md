# 🍎 FruitStore API – Spring Boot + JWT Auth + Role Based Access + Forget/Reset Password

FruitStore is a Spring Boot REST API project built to practice and master **Spring Security** with **JWT Authentication** and **Role-Based Access Control (RBAC)**.

This project contains 3 roles:

* **USER** → can only **read fruits** + manage own profile
* **MANAGER** → can **CRUD fruits** + delete only normal users
* **ADMIN** → can do **everything** + promote/demote roles + manage managers/users

---

## ✅ Features

### 🔐 Authentication (JWT)

* Register user
* Login user
* JWT token generation & validation
* Secured endpoints with role-based access
* Forgot Password & Reset Password Implementation

### 👥 Role Based Access

* USER / MANAGER / ADMIN roles
* Admin can promote/demote users to manager
* Admin can delete managers and users (cannot delete admin)
* Manager can delete only normal users

### 🍉 Fruit Module

* Get all fruits (USER/MANAGER/ADMIN)
* Create fruit (MANAGER/ADMIN)
* Update fruit (MANAGER/ADMIN)
* Delete fruit (MANAGER/ADMIN)

### 👤 User Profile

* Get logged-in user profile
* Delete own profile
* Change password (with old password validation)

### ✅ Swagger API Docs

* Interactive API testing via Swagger UI

### ✅ Global Exception Handling

* Validation errors
* Conflict errors (duplicate email)
* Not found exceptions
* Forbidden access scenarios

---

## 🧱 Tech Stack

* **Java 17**
* **Spring Boot 3.3.7**
* **Spring Security**
* **JWT (JJWT)**
* **PostgreSQL**
* **Spring Data JPA**
* **Swagger (springdoc-openapi)**
* **Maven**

---

## 📌 Roles and Permissions

| Role    | Fruits Access | User Management                          |
| ------- | ------------- | ---------------------------------------- |
| USER    | ✅ Read only   | ✅ Self profile only                      |
| MANAGER | ✅ CRUD        | ✅ Delete normal users                    |
| ADMIN   | ✅ CRUD        | ✅ Promote/Demote + Delete users/managers |

---

## 📂 Project Structure

```
com.example.fruitStore
│
├── FruitStoreApplication.java
│
├── config
│   ├── SwaggerConfig.java
│   └── exception
│       ├── GlobalExceptionHandler.java
│       ├── NotFoundException.java
│       ├── ConflictException.java
│       ├── BadRequestException.java
│       └── ForbiddenException.java
│
├── mail
│   ├──EmailService.java
│   └──EmailServiceImpl.java
│
├── security
│   ├── SecurityConfig.java
│   ├── JwtAuthFilter.java
│   ├── JwtService.java
│   ├── CustomUserDetailsService.java
│   ├── CustomAuthEntryPoint.java
│   └── CustomAccessDeniedHandler.java
│
├── auth
│   ├── controller
│   │   └── AuthController.java
│   ├── dto
│   │   ├── RegisterRequest.java
│   │   ├── LoginRequest.java
│   │   ├── AuthResponse.java
│   │   ├── ForgotPasswordRequest.java
│   │   └── ResetPasswordRequest.java
│   ├── entity
│   │   └── PasswordResetToken.java 
│   ├── repository 
│   │       └── PasswordResetTokenRepository.java
│   └── service
│       ├── AuthService.java
│       ├── PasswordResetService.java
│       └── impl
│           ├──PasswordResetServiceImpl.java
│           └── AuthServiceImpl.java
│
├── user
│   ├── controller
│   │   ├── AdminController.java
│   │   ├── ManagerController.java
│   │   └── UserController.java
│   ├── dto
│   │   ├── UserResponse.java
│   │   └── ChangePasswordRequest.java
│   ├── entity
│   │   ├── User.java
│   │   └── Role.java
│   ├── repository
│   │   └── UserRepository.java
│   └── service
│       ├── UserService.java
│       └── impl
│           └── UserServiceImpl.java
│
└── fruit
    ├── controller
    │   └── FruitController.java
    ├── dto
    │   ├── FruitRequest.java
    │   └── FruitResponse.java
    ├── entity
    │   └── Fruit.java
    ├── repository
    │   └── FruitRepository.java
    └── service
        ├── FruitService.java
        └── impl
            └── FruitServiceImpl.java
```

---

## ⚙️ Setup Instructions

### ✅ 1) Clone repository

```bash
git clone <your-repo-url>
cd fruitStore
```

### ✅ 2) Create PostgreSQL DB

Create DB:

```
fruits_dev
```

Create role/user (example):

```
fruits_dev_app
```

Grant permissions to role (schema + tables).

---

### ✅ 3) Configure `application.properties`

```properties
# PostgreSQL DB connection
spring.datasource.url=jdbc:postgresql://localhost:5432/fruits_dev
spring.datasource.username=fruits_dev_app
spring.datasource.password=YOUR_PASSWORD
spring.datasource.driver-class-name=org.postgresql.Driver

# Hibernate settings
spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true

# JWT secret (any long random string)
app.jwt.secret=THIS_IS_A_SUPER_LONG_SECRET_KEY_CHANGE_IT
app.jwt.expiration-ms=3600000

# Email configuration - Mailtrap (DEV) & AWS SES (Prod)
spring.mail.host=sandbox.smtp.mailtrap.io
spring.mail.port=587
spring.mail.username=YOUR_MAILTRAP_USERNAME
spring.mail.password=YOUR_MAILTRAP_PASSWORD
spring.mail.from=no-reply@fruitstore.dev
spring.mail.properties.mail.smtp.auth=true
spring.mail.properties.mail.smtp.starttls.enable=true

app.reset-password.base-url=http://localhost:3000/reset-password
app.mail.app-name=Fruit Store
```

---

### ✅ 4) Run project

```bash
mvn clean install
mvn spring-boot:run
```

---

## 📘 Swagger API Docs

After running, open:

✅ Swagger UI

```
http://localhost:8080/swagger-ui/index.html
```

✅ OpenAPI Docs JSON

```
http://localhost:8080/v3/api-docs
```

---

## 🔥 API Endpoints

### ✅ Auth

| Method | Endpoint             | Access |
| ------ | -------------------- | ------ |
| POST   | `/api/auth/register` | Public |
| POST   | `/api/auth/login`    | Public |

---

### ✅ Fruits

| Method | Endpoint           | Access                 |
| ------ | ------------------ | ---------------------- |
| GET    | `/api/fruits`      | USER / MANAGER / ADMIN |
| POST   | `/api/fruits`      | MANAGER / ADMIN        |
| PUT    | `/api/fruits/{id}` | MANAGER / ADMIN        |
| DELETE | `/api/fruits/{id}` | MANAGER / ADMIN        |

---

### ✅ Admin

| Method | Endpoint                                   | Access |
| ------ | ------------------------------------------ | ------ |
| PUT    | `/api/admin/users/{id}/promote-to-manager` | ADMIN  |
| PUT    | `/api/admin/users/{id}/demote-to-user`     | ADMIN  |
| DELETE | `/api/admin/users/{id}`                    | ADMIN  |
| GET    | `/api/admin/managers`                      | ADMIN  |

---

### ✅ Manager

| Method | Endpoint                  | Access          |
| ------ | ------------------------- | --------------- |
| DELETE | `/api/manager/users/{id}` | MANAGER / ADMIN |

---

### ✅ User Profile

| Method | Endpoint                | Access        |
| ------ | ----------------------- | ------------- |
| GET    | `/api/user/me`          | Authenticated |
| DELETE | `/api/user/me`          | Authenticated |
| PUT    | `/api/user/me/password` | Authenticated |

---

## 🔑 JWT Usage

After login, you will receive:

```json
{
  "token": "eyJhbGciOiJIUzI1NiIsInR5cCI..."
}
```

For secured endpoints, pass token in headers:

```
Authorization: Bearer <TOKEN>
```

---

## ✅ Testing Roles Quickly

### ✅ Create Admin (first time only)

After registering a normal user, run in DB:

```sql
UPDATE tb_users SET role = 'ADMIN' WHERE email = 'your_email@gmail.com';
```

Login again → you become ADMIN.

---

## 🚀 Next Step

✅ Integrating **Google OAuth Login** with this same JWT + RBAC system.

---

## 👨‍💻 Author

**Vijay Tamada**

