# 🚗 Trimble Cars - Car Lease Management System

Trimble Cars is a Spring Boot-based backend application that allows car owners to lease their vehicles and customers to rent them. Admins have full access to manage users, cars, and leases. The application uses in-memory H2 for simplicity and exposes RESTful APIs for managing the complete lease lifecycle.

---

## 📦 Tech Stack

- **Java 17**
- **Spring Boot**
- **Spring Data JPA**
- **H2 In-Memory Database**
- **JUnit 5 + Mockito**
- **Swagger / OpenAPI** for API documentation
- **Gradle or Maven** (choose your build tool)
- **RESTful API** (Postman-friendly)

---

## 👥 User Roles

| Role     | Description                                  |
|----------|----------------------------------------------|
| Owner    | Registers cars, tracks lease status/history  |
| Customer | Leases and returns cars                      |
| Admin    | Full access: manages all users, cars, leases |

---
📂 Project Structure
com.trimblecars
├── controller
├── dto
├── entity
├── enums
├── exception
├── repository
├── service
│   └── impl
├── util (for mappers)
└── config (Swagger config)

Test coverage:
✅ Unit Tests for CarServiceImpl, LeaseServiceImpl, UserServiceImpl
✅ Functional Tests for all controllers using MockMvc
✅ Exception handling coverage for:
UserNotFoundException
CarNotFoundException
LeaseNotFoundException
BusinessRuleException

 Swagger API Docs
Once the app is running, visit: http://localhost:8080/swagger-ui.html
| Tag       | Description                        |
| --------- | ---------------------------------- |
| Customer  | Lease operations for customers     |
| Car Owner | Car and history management         |
| Admin     | Full admin control over the system |

 Key API Endpoints
🔐 Customer
Method	Endpoint	Description
GET	/customer/cars	View all available cars
POST	/customer/start-lease	Start a lease
POST	/customer/end-lease	End a lease
GET	/customer/leases/{customerId}	View lease history

🧑‍🔧 Car Owner
Method	Endpoint	Description
POST	/car-owner/register-car	Register a car
GET	/car-owner/cars/{ownerId}	Get owner’s cars
GET	/car-owner/car/{carId}/lease-history	Car lease history

👑 Admin
Method	Endpoint	Description
POST	/admin/register-user	Register any user
GET	/admin/users/{role}	Get users by role

🗃 Database
H2 Console: http://localhost:8080/h2-console

👤 Author
Jaya Suriya.K

