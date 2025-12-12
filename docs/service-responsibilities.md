# Service Responsibilities (v1 snapshot)

## Auth Service
- Register / Login
- Issue JWT
- Store users & roles in MySQL

## Product Service
- CRUD product data
- Sync searchable data to Elasticsearch

## Inventory Service
- Maintain stock counts (DB + Redis cache)
- Respond to ORDER_PLACED / ORDER_CANCELLED events

## Order Service
- Create & manage orders
- Publish ORDER_PLACED events

## Payment Service
- Integrate with payment gateway sandbox
- Verify webhooks and publish PAYMENT_SUCCESS

## Review Service
- Store reviews in MongoDB
- Aggregate ratings and notify Product Service

## Notification Service
- Consume events and send emails/SMS
