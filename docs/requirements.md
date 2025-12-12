# Acceptance Criteria (core)

## Users / Roles
- Customer: browse, add to cart, checkout, view orders
- Seller: register, add/edit products, view orders for their products
- Admin: manage users, moderate products

## Core Features
- Search: full-text + filters
- Cart: persistent session-backed cart
- Orders: PLACED -> PAID -> SHIPPED -> DELIVERED -> CANCELLED
- Payment: sandbox integration with webhook validation
- Reviews: customers can submit reviews; product rating updates
- Notifications: email on key events

## Advanced
- Event-driven communication (Kafka)
- Redis caching for hot reads
- Elasticsearch for search
