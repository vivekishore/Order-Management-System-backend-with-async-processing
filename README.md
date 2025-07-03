# Order Management System

---

## Project Overview

This project implements a RESTful API for managing customers and their orders with asynchronous order finalization. It supports:

- CRUD operations for customers and orders
- Soft delete for orders with audit
- Background processing to calculate order total and update status
- Order status updates and validation
- Endpoint to retrieve order total for confirmed and shipped orders

---

## Key Features

- **Order Creation:** Accepts new orders with "Pending" status and triggers async finalization
- **Async Finalization:** Calculates total order value and updates order status to "Confirmed"
- **Order Status Update:** Allows manual updates to order status (e.g., "Shipped")
- **Soft Delete:** Implements soft delete to mark orders as deleted without removing data
- **Total Value Endpoint:** Returns pre-calculated total only for confirmed/shipped/delivered orders
- **Concurrency Handling:** Uses optimistic locking/versioning for safe concurrent updates
- **Validation:** Input validation on API requests to ensure data integrity

---

## Technologies Used

- Java 17
- Spring Boot 3
- Spring Data JPA with Hibernate
- MySQL (or any relational DB)
- Lombok for boilerplate code reduction
- Maven for build and dependency management

---

## Postman Collection

You can use the included Postman collection to test the API endpoints easily.
- File path: Order-Management-System-backend-with-async-processing/OrderManagementSystem.postman_collection_v3.json
- Import it into Postman by clicking **Import** > **Upload Files** > select the JSON file.

---

## Setup and Running

1. **Clone the repository**

```bash
git clone https://github.com/vivekishore/Order-Management-System-backend-with-async-processing.git
cd Order-Management-System-backend-with-async-processing
