# E-Plant-Shopping

E-Plant-Shopping is a modern e-commerce web application for plant shopping. The frontend is built with **e-plant-shopping**, and the backend uses a microservices architecture for scalability and maintainability.

## Architecture Overview

- **Frontend:**  
    - `e-plant-shopping` (React)

- **Backend Microservices:** (Spring Boot)
    - `gatewayservice` - API Gateway for routing and authentication
    - `orderservice` - Handles order management and processing
    - `userservice` - Manages user accounts and authentication
    - `shoppingplantservice` - Manages plant catalog and inventory
    - `registryservice` - Service discovery and registration

## Features

- Browse and search for plants
- User registration and authentication
- Shopping cart and order placement
- Order history and tracking
- Secure API gateway and service discovery

## Getting Started

1. **Clone the repository**
2. **Start the backend microservices**  
     Each service runs independently 
     mvn spring-boot:run
3. **Run the frontend**  
     Navigate to `e-plant-shopping` and start the frontend server.

## Project Structure

```
e-plant-shopping/
    ├── gatewayservice/
    ├── orderservice/
    ├── userservice/
    ├── shoppingplantservice/
    ├── registryservice/
    └── e-plant-shopping/ (frontend)
```

### Front-end

🌿 Paradise Nursery - E-Commerce Frontend

Paradise Nursery is a React-based e-commerce frontend application that allows users to browse, search, and purchase plant products. It supports user authentication, cart management, order history, and category-based filtering. It is integrated with a backend API for dynamic data operations and uses Redux for cart state management.

Features

👋 Landing Page

About Us section introducing Paradise Nursery's mission.

"Visit our Products" button navigates users based on login status:

Logged-in: redirected to /shopping.

Not logged-in: redirected to /view-products.

🛍 Product Pages

/view-products: Public product list.

/shopping: Private product list with "Add to Cart" feature.

Search bar and category filter with dynamic dropdown.

Stock-aware product display (disables "Add to Cart" if out of stock).

🛒 Cart Management

/shopping-cart route:

View items in cart.

Increase/decrease quantities.

Remove products.

Checkout (confirm order).

Total price calculation.

Redux-based cart logic with persistent syncing post-login.

🧾 Order History

/orders route:

View past orders with expandable details.

Cancel eligible orders.

Displays unit price, quantity, and order metadata.

🔐 Authentication

/login route:

Authenticate via username & password.

Loads user's cart and order info post-login.

/register route:

Signup with username, email, and password.

Error handling for duplicates and invalid input.

Stores username in sessionStorage.

📦 Backend API Integration

All data fetched from the /api endpoint.

Axios instance with withCredentials: true.

Utility functions: fetchData, postData, putData, and deleteData with unified error handling.

Handles JWT authentication via HttpOnly cookies.

Basic CSRF handling using the X-XSRF-TOKEN header.

Technologies Used

React – Core framework.

Redux Toolkit – Cart state management.

React Router – Routing between views.

Axios – For HTTP requests.

Bootstrap + CSS – Styling.

Session Storage – Persist login state client-side.

├── App.jsx                     # Main application component with routing

├── components/

│   ├── Navbar.jsx             # Dynamic navbar with login/logout/cart

│   ├── AboutUs.jsx            # Landing page content

│   ├── ProductList.jsx        # Product view for logged-in users

│   ├── ProductListNotLogged.jsx # Product view for visitors

│   ├── CartItem.jsx           # Shopping cart view

│   ├── Orders.jsx             # Order history view

│   ├── Login.jsx              # Login form and logic

│   └── Register.jsx           # User registration

├── redux

│   └── CartSlice.js           # Redux logic for cart actions

├── utils/

│   └── api.js                 # Axios instance and API helper functions

API Endpoints Used

All endpoints are relative to /api, including:

Products: /products/list, /products/name/{name}, /products/category, etc.

Cart: /user/add-to-cart, /user/increase-unit/product/{id}, /user/view-cart, etc.

Orders: /user/view-orders, /user/view-order-details/{order_id}, /user/confirm-order, etc.

Auth: /auth/login, /auth/logout, /auth/register

Security Notes

Uses HttpOnly JWT cookies for authentication.

CORS is configured for http://localhost:3000.

!Needs to implement CSRF token handling via XSRF-TOKEN cookie and custom header.

### Run

npm install

npm start

Ensure backend is running on port 8080 or use proxy configuration to route /api calls.

### Back-end

E-Commerce Microservices Platform

This project is a microservices-based e-commerce platform built with Spring Boot and Spring Cloud. It consists of multiple services working together to provide a complete shopping experience. Architecture Overview

Services

User Service - Gateway service handling authentication, user management, and routing

Config Server - Centralized configuration management

Registry Service - Eureka server for service discovery

Order Service - Manages shopping carts and orders

Product Service - Handles product catalog and inventory

MySQL Databases - Data persistence for each service

For each microservice, changing the port
Service Ports

Service Port

Config Server 8888

Registry Service 8761

User Service 8080

Product Service 8081

Order Service 8082


API Documentation
User Service Endpoints
Authentication

POST /api/auth/login - User login

Body: { "username" : "", "password" : "" }

POST /api/auth/register - User registration, sends to the e-mail a token

Body: { "username" : "", "password" : "", "email" : "....@gmail.com" }

POST /api/auth/register/admin - Method to register an admin, sends to the e-mail a token

GET /api/products/list - List all products

GET /api/products/{id} - Get product by ID

GET /api/products/name/{name} - List products containing the name

GET /api/products/category/{category} - List products from a category

User Operations

GET /api/users/info - Get user info

POST /api/users/resetpwd method to change password, revokes previous tokens

POST /api/users/changemail method to change mail, sends new OTP code to verify, if not done in time, it resets the previous mail

POST /api/users/add-to-cart - Add to cart

Body: { "product_id" : 5, "quantity" : 1 }

GET /api/users/view-cart - View cart

DELETE /api/users/remove-from-cart/product/{product_id} remove an item from cart

DELETE /api/users/empty-cart remove all items from cart

DELETE /api/users/cancel-order/{order_id} cancells an order, refills products stock

PUT /api/users/confirm-order confirms cart, moves from "Not completed" to "Processing", awating payment

PUT /api/users/reduce-unit/product/{product_id} reduce one unit from a product in cart

PUT /api/users/increase-unit/product/{product_id} adds one unit from a product in cart

GET /api/users/view-orders View your orders 

GET /api/users/view-order-details/{order_id} view the products from an order

Admin Operations

DELETE /api/admin/delete/{id} - Delete user

DELETE /api/admin/delete/non-verified - Delete non verified users, there is a scheduled service to erase non verified users each hour from db

DELETE /api/admin/products/delete/{id} - Delete a product

POST /api/admin/products/add - Add product

PUT /api/admin/products/refill/{id} - Refill product stock

PUT /api/admin/products/product/{id}/price/{price} - change product Price

PUT /api/admin/products/update/{id} - update a product

DELETE /api/admin/cancel-order/{order_id} cancel an order

GET /api/admin/view-orders view orders

GET /api/admin/view-order-details/{order_id} view orders details

For User and Admin operations, include in the cookies request the JWT token

Limit service for resetpwd 3 resets per 5 minutes, and for login 5 logins per 5 minutes

