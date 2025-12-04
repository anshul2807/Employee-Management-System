

# 🚀 Employee Management System (EMS) – Backend

A robust, secure, and scalable backend service designed to manage employee data, authentication, authorization, and administrative lookup resources. This service powers the Employee Management System (EMS) and exposes a fully RESTful API built using **Spring Boot** and **MongoDB**, with security enforced via **Spring Security + JWT**.

---

## 📖 Table of Contents

* [Overview](#-overview)
* [Features](#-features)
* [Technology Stack](#-technology-stack)
* [Architecture](#-architecture)
* [Installation](#-installation)
* [Configuration](#-configuration)
* [Running the Application](#-running-the-application)
* [Security & Authorization](#-security--authorization)
* [API Reference](#-api-reference)

    * [Authentication & Users](#i-authentication--user-auth)
    * [Employee Management](#ii-employee-management-employee)
    * [Lookup Data](#iii-lookup-data-lookups)
* [Troubleshooting](#-troubleshooting)
* [Contributors](#-contributors)
* [License](#-license)

---

## 📝 Overview

The **Employee Management System (EMS) Backend** provides the core API for managing:

* User authentication & authorization
* Employee records
* Organization lookups (Departments, Job Titles)
* Reporting hierarchy

It is built for enterprise environments requiring both flexibility and security, with separate permissions for **Administrators** and regular authenticated users.

---

## ✨ Features

### 🔐 Secure Authentication

* Login and registration flows using **JWT**.
* Password change endpoints for users and admins.

### 🔑 Role-Based Access Control (RBAC)

* Granular authorization with roles:

    * `ADMIN`
    * Authenticated `USER`

### 👥 Employee Management

* Full CRUD operations for employee records (Admin only)
* Self-service updates for users (email, phone, DOB)
* Manager report querying (direct reports)

### 🧩 Administrative Lookups

* Manage static organizational lists:

    * Departments
    * Job Titles

---

## 🛠️ Technology Stack

| Component         | Technology            | Description                                   |
| ----------------- | --------------------- | --------------------------------------------- |
| Backend Framework | Spring Boot           | Main framework for RESTful API development    |
| Database          | MongoDB               | NoSQL database for flexible document storage  |
| Security          | Spring Security + JWT | Authentication, authorization, token issuance |
| Language          | Java                  | Core application language                     |
| Build Tool        | Maven                 | Dependency management & build system          |

---

## 🏛️ Architecture

The system follows a **layered architecture**:

```
Controller → Service → Repository → MongoDB
         ↘ Security Layer (JWT, Filters, RBAC)
```

* **SecurityFilterChain** enforces endpoint permissions.
* **DTOs** are used for request/response serialization.
* **MongoDB collections** store employees, users, and lookup records.


---

## ⚙️ Installation

### **Prerequisites**

Ensure the following are installed:

* **Java 17+**
* **MongoDB** (local or remote instance)
* **Maven**

---

## 🔧 Configuration

Update your `application.properties` or `application.yml`:

```properties
spring.data.mongodb.uri=mongodb://localhost:27017/ems_database
```

Replace values as needed for your environment.

---

## ▶️ Running the Application

### 1. Clone the Repository

```bash
git clone <your-repository-url>
cd EmployeeManagementSystem
```

### 2. Build the Project

```bash
mvn clean install
```

### 3. Run the JAR

```bash
java -jar target/<your-artifact-name>.jar
```

The service will start on **[http://localhost:8080](http://localhost:8080)**.

---

## 🔒 Security & Authorization

Authentication uses **JWT**.

Include your token in all protected requests:

```
Authorization: Bearer <jwt_token>
```

### **Access Rules Summary**

| Endpoint Pattern         | Access Level  | Description             |
| ------------------------ | ------------- | ----------------------- |
| `/auth/login`            | Public        | Obtain JWT              |
| `/lookups/**`            | Authenticated | Retrieve lookup data    |
| `/employee/updateByUser` | Authenticated | User self-updates       |
| `/auth/pwd-change`       | Authenticated | User password change    |
| `/employee/**`           | ADMIN         | All other employee ops  |
| `/auth/**`               | ADMIN         | All other auth/user ops |

---

## 🗺️ API Reference

### I. Authentication & User (`/auth`)

| Method | Route                    | Description                       | Permissions   |
| ------ | ------------------------ | --------------------------------- | ------------- |
| POST   | `/auth/register`         | Register new user (Admin only)    | ADMIN Only    |
| GET    | `/auth/login`            | Login and receive JWT             | Public        |
| PUT    | `/auth/pwd-change`       | User changes own password         | Authenticated |
| PUT    | `/auth/admin-pwd-change` | Admin changes any user's password | ADMIN Only    |
| GET    | `/auth/all-users`        | List all users                    | ADMIN Only    |

---

### II. Employee Management (`/employee`)

| Method | Route                           | Description                       | Permissions   |
| ------ | ------------------------------- | --------------------------------- | ------------- |
| POST   | `/employee`                     | Create new employee               | ADMIN Only    |
| GET    | `/employee`                     | Get all employees                 | ADMIN Only    |
| GET    | `/employee/{myid}`              | Get employee by ID                | ADMIN Only    |
| PUT    | `/employee/updateByUser`        | User updates email/phone/DOB      | Authenticated |
| PUT    | `/employee/updateByAdmin`       | Admin updates all employee fields | ADMIN Only    |
| DELETE | `/employee/{myid}/terminate`    | Remove employee & associated user | ADMIN Only    |
| GET    | `/employee/{managerId}/reports` | Get manager’s direct reports      | ADMIN Only    |

---

### III. Lookup Data (`/lookups`)

| Method | Route                  | Description       | Permissions   |
| ------ | ---------------------- | ----------------- | ------------- |
| GET    | `/lookups/departments` | List departments  | Authenticated |
| POST   | `/lookups/departments` | Create department | ADMIN Only    |
| GET    | `/lookups/job-titles`  | List job titles   | Authenticated |
| POST   | `/lookups/job-titles`  | Create job title  | ADMIN Only    |

---

## 🛠️ Troubleshooting

### ❗ MongoDB connection errors

* Ensure MongoDB is running:

  ```bash
  sudo service mongod start
  ```
* Verify your URI is valid and DB user has proper permissions.

### ❗ 403 Forbidden

* Token may be missing or expired.
* Ensure role matches the required access level.

### ❗ 401 Unauthorized

* Incorrect credentials during login.
* Token malformed or not included in `Authorization` header.

---

## 👨‍💻 Contributors

* **Anshul singh**, the project owner


