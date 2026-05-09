# AdmissionPortal




# 🎓 Spring Boot Admission Portal

A production-ready backend system built with **Spring Boot + JWT Security + Spring Data JPA + MySQL**.

---

## 🚀 Features

- ✅ JWT Authentication (Stateless — no session)
- ✅ Role-based Authorization
- ✅ Candidate Profile Management
- ✅ Admission Module
- ✅ Secured REST APIs
- ✅ Spring Data JPA + MySQL

---

## 🏗️ Project Structure

```
com.portal/
├── Application.java              # Main entry point
│
├── iam/                          # Identity & Access Management
│   ├── IamController.java        # POST /api/iam/login
│   ├── IamService.java           # Login logic + JWT generation
│   ├── IamRepository.java        # Users table
│   └── security/
│       ├── JwtUtil.java          # Token generate + validate
│       ├── JwtAuthFilter.java    # Intercepts every request
│       └── SecurityConfig.java   # Wires security rules
│
├── profile/                      # Candidate Profiles
│   ├── ProfileController.java    # POST /api/profile/save
│   ├── ProfileService.java       # Profile logic
│   └── ProfileRepository.java    # Profiles table
│
└── admission/                    # Core Admissions
    ├── AdmissionController.java
    ├── AdmissionService.java
    └── AdmissionRepository.java
```

---

## 🔐 How JWT Works Here

```
1. POST /api/iam/login  →  { email, password }
2. Server validates credentials
3. Server returns JWT token
4. Client sends token in every request:
   Authorization: Bearer <token>
5. JwtAuthFilter validates token
6. ✅ Valid → request passes through
   ❌ Invalid → 403 Forbidden
```

---

## ⚙️ Tech Stack

| Technology | Purpose |
|-----------|---------|
| Spring Boot 3 | Backend framework |
| Spring Security | API protection |
| JWT (jjwt) | Stateless authentication |
| Spring Data JPA | Database layer |
| MySQL | Database |
| Maven | Build tool |

---

## 🗄️ Database Setup

```sql
CREATE DATABASE admission_portal;
USE admission_portal;

CREATE TABLE users (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    email VARCHAR(100) UNIQUE NOT NULL,
    password VARCHAR(255) NOT NULL,
    role VARCHAR(20) DEFAULT 'USER'
);
```

---

## 🔌 Configuration

Update `application.properties`:

```properties
spring.datasource.url=jdbc:mysql://localhost:3306/admission_portal
spring.datasource.username=root
spring.datasource.password=root
spring.jpa.hibernate.ddl-auto=update

jwt.secret=your-secret-key-here
jwt.expiration=36000000
```

---

## ▶️ How to Run

```bash
# Clone the repo
git clone https://github.com/subodhkmahto/spring-boot-admission-app.git

# Navigate to project
cd spring-boot-admission-app

# Run with Maven
mvn spring-boot:run
```

Server starts at: `http://localhost:8080`

---

## 📡 API Endpoints

| Method | Endpoint | Auth Required | Description |
|--------|----------|--------------|-------------|
| POST | /api/iam/login | ❌ Public | Login + get JWT |
| POST | /api/profile/save | ✅ Bearer Token | Save candidate profile |
| GET | /api/admission/all | ✅ Bearer Token | Get all admissions |

---

## 🧠 Key Concepts Covered

- JWT Stateless Authentication
- OncePerRequestFilter (JwtAuthFilter)
- Spring Security filter chain
- Role-based access control
- Clean layered architecture (Controller → Service → Repository)
- JPA entity mapping

---

## 🔥 Why JWT over Sessions?

| Session | JWT |
|---------|-----|
| Server stores state | Client stores token |
| DB hit every request | No DB hit — math proves it |
| Server restart = logout | Server restart = stay logged in |
| Hard to scale | Easy to scale |

---

## 👨‍💻 Author

**Subodh Kumar**
- 💼 [LinkedIn](https://www.linkedin.com/in/subodh-kumar-53176924b/)
- 🐙 [GitHub](https://github.com/subodhkmahto)

---

## ⭐ If you found this helpful

Give it a ⭐ on GitHub and share it with someone learning Spring Boot!

`#Java` `#SpringBoot` `#JWT` `#100DaysOfJava`
