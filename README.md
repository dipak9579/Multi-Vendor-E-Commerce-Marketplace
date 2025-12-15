# 🛒 Multi-Vendor E-Commerce Platform (Microservices Architecture)

A **production-grade multi-vendor e-commerce system** built using **Spring Boot, Spring Cloud, JWT Security, MySQL, Eureka, OpenFeign, and API Gateway**.
This project simulates real-world flows like **order placement, inventory reservation, payment processing, and role-based access control**.

---

## 🚀 Key Features

### 🔐 Authentication & Authorization

* JWT-based authentication
* Role-based access control (RBAC)
* Roles supported:

  * **ADMIN** – approve sellers, manage system
  * **SELLER** – manage products & inventory
  * **CUSTOMER** – cart, order, payment

### 🧩 Microservices Architecture

* Independent services with **database per service**
* Service discovery using **Netflix Eureka**
* Inter-service communication via **OpenFeign**
* Centralized routing & security via **API Gateway**

### 📦 Core Business Flow

```
Customer → Cart → Order → Inventory Reserve → Payment → Order Status Update
```

* Inventory reserved before payment
* Payment simulated with success/failure
* Orders remain retryable on payment failure

---

## 🏗️ Tech Stack

| Layer             | Technology                  |
| ----------------- | --------------------------- |
| Language          | Java 17                     |
| Framework         | Spring Boot 3.x             |
| Security          | Spring Security + JWT       |
| Microservices     | Spring Cloud                |
| Service Discovery | Eureka                      |
| API Gateway       | Spring Cloud Gateway        |
| DB                | MySQL                       |
| ORM               | Spring Data JPA / Hibernate |
| Communication     | OpenFeign                   |
| Build Tool        | Maven                       |
| Testing           | Postman                     |

---

## 🧱 Microservices Overview

| Service              | Port | Description                   |
| -------------------- | ---- | ----------------------------- |
| API Gateway          | 8080 | Routing, JWT validation, RBAC |
| Auth Service         | 8081 | Login, Register, JWT issuing  |
| User Service         | 8082 | User profile management       |
| Seller Service       | 8083 | Seller onboarding & approval  |
| Product Service      | 8084 | Product catalog               |
| Inventory Service    | 8085 | Stock management              |
| Cart Service         | 8086 | Customer cart                 |
| Order Service        | 8087 | Order lifecycle               |
| Payment Service      | 8088 | Payment processing            |
| Notification Service | 8089 | Email / log notifications     |
| Eureka Server        | 8761 | Service registry              |

---

## 🔁 Order Lifecycle

```text
CREATED
  ↓
PAYMENT_PENDING
  ↓
PAID / PAYMENT_FAILED
  ↓
SHIPPED → DELIVERED
```

* Payment failures do **not crash the system**
* Retry allowed until payment succeeds

---

## 🔐 API Gateway – Security Rules

* JWT validated at gateway level
* User context propagated using headers:

  * `X-USER`
  * `X-ROLES`

### Role Mapping

| API               | Role     |
| ----------------- | -------- |
| /api/admin/**     | ADMIN    |
| /api/sellers/**   | SELLER   |
| /api/products/**  | SELLER   |
| /api/inventory/** | SELLER   |
| /api/cart/**      | CUSTOMER |
| /api/orders/**    | CUSTOMER |
| /api/payments/**  | CUSTOMER |

---

## 📬 Important API Examples

### 🛍️ Place Order

```
POST /api/orders
Header: Authorization: Bearer <JWT>
Body:
{
  "items": [
    {
      "productId": 4,
      "quantity": 2,
      "price": 15000
    }
  ]
}
```

### 💳 Initiate Payment

```
POST /api/payments/initiate
Header: Authorization: Bearer <JWT>
Body:
{
  "orderId": 12,
  "amount": 30000
}
```

### 📦 My Orders

```
GET /api/orders/me
Header: Authorization: Bearer <JWT>
```

---

## 🧪 Sample Payment Response

```json
{
  "id": 9,
  "orderId": 12,
  "amount": 45000.0,
  "status": "FAILED",
  "createdAt": "2025-12-16T00:42:03"
}
```

> Payment failure is treated as a **business outcome**, not an exception.

---

## 🛠️ How to Run the Project

### 1️⃣ Start Infrastructure

* MySQL running
* Create databases:

  * auth_db, user_db, product_db, inventory_db, cart_db, order_db, payment_db

### 2️⃣ Start Services (Order matters)

1. Eureka Server
2. API Gateway
3. Auth Service
4. Other services (any order)

### 3️⃣ Test via Postman

* Register → Login → Copy JWT
* Use JWT in Authorization header

---

## 🎯 Resume Highlights

✔ Real-world microservices architecture
✔ JWT security at gateway level
✔ Role-based authorization
✔ Database per service
✔ Fault-tolerant payment workflow
✔ Production-ready service design

---

## 📌 Future Enhancements

* Kafka-based event communication
* Saga pattern for order rollback
* Redis caching
* Docker & Kubernetes deployment
* Payment gateway integration (Razorpay/Stripe)

---

## 👨‍💻 Author

**Dipak Dandge**
Java Backend Developer

---

⭐ If you like this project, don’t forget to star the repository!
