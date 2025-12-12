# Architecture Overview

This project uses a microservices architecture:

- Frontend: React + Redux (client)
- API Gateway: Spring Cloud Gateway (single entry)
- Service Discovery: Eureka
- Services: Auth, Product, Inventory, Order, Payment, Seller, Review, Notification
- Data stores: MySQL (core services), MongoDB (reviews), Elasticsearch (search), Redis (cache)
- Messaging: Kafka (event-driven)
- Deployment: Docker (compose) and optional Kubernetes

See service-responsibilities.md for service-level ownership and endpoints.
