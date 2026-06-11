# Canteen Management and Ordering Backend System

A Spring Boot based backend application that enables digital food ordering, menu management, user management, and payment integration for college or organizational canteens.

## Features

###  User Management
- User Registration
- User Login
- Role-based Access (Admin / Customer)

### Menu Management
- Add new food items
- Update food item details
- Delete food items
- View available menu

### Order Management
- Place orders
- View order history
- Track order status
- Manage customer orders

### Payment Integration
- Razorpay payment gateway integration
- Payment verification
- Payment record management

### 📊 Admin Features
- Manage menu items
- View all orders
- Update order status
- Monitor payments

---

##  Tech Stack

- Java 17
- Spring Boot
- Spring Data JPA
- Hibernate
- MySQL
- Maven / Gradle
- Razorpay API
- REST APIs

---

## Project Structure

```
src
├── controller
├── service
├── repository
├── entity
├── dto
├── config
└── exception
```

---

##  Installation & Setup

### Clone Repository

```bash
git clone https://github.com/your-username/Canteen-Management-and-Ordering-Backend-System.git
```

### 2️⃣ Configure Database

Update `application.properties`

```properties
spring.datasource.url=jdbc:mysql://localhost:3306/canteen_db
spring.datasource.username=root
spring.datasource.password=your_password
```

### Build Project

```bash
./mvnw clean install
```

or

```bash
./gradlew build
```

###  Run Application

```bash
./mvnw spring-boot:run
```

or

```bash
./gradlew bootRun
```

---

##  API Modules

- Authentication APIs
- User APIs
- Menu APIs
- Order APIs
- Payment APIs

---

## Future Enhancements

- JWT Authentication
- Real-time Order Tracking
- Email Notifications
- Analytics Dashboard
- Mobile App Integration

---

##  Author

**Yashika Sinha**

B.Tech Computer Science Student
