# 💰 Money Saver - Java MVC Console Application

Money Saver is a console-based personal finance management system developed using Java and SQL.  
The application helps users track income, expenses, balance, and spending categories efficiently using a clean MVC architecture.

---

# 📌 Project Overview

Managing personal expenses manually is difficult and error-prone.  
Money Saver solves this problem by providing a simple finance tracking system where users can:

- Register and login securely
- Add income and expenses
- Track transaction history
- Monitor total balance
- Categorize spending
- View spending reports

This project is developed as a learning-focused real-world Java application using:
- Java
- JDBC
- SQL Database
- MVC Architecture

---

# 🚀 Tech Stack

| Technology | Usage |
|------------|------|
| Java | Core Application |
| JDBC | Database Connectivity |
| SQL | Data Storage |
| MVC Architecture | Project Structure |
| IntelliJ IDEA | Development IDE |

---

# 🏗️ Architecture

The project follows the MVC (Model View Controller) pattern.

## MVC Structure

### Model
Handles:
- Database entities
- Business data
- Database operations

### View
Handles:
- Console UI
- User interaction
- Menu display

### Controller
Handles:
- Business logic
- Request handling
- Validation

---

# 👨‍💻 Features

## Authentication
- User Registration
- User Login
- Password validation

## Income Management
- Add income
- Store income records

## Expense Management
- Add expenses
- Categorize expenses
- Track spending

## Transaction Management
- View transaction history
- Filter transactions
- Daily/monthly tracking

## Balance Management
- Calculate total balance
- Remaining amount display

## Reports
- Category-wise expense report
- Monthly expense report
- Daily report

---

# 📌 Project Phases

## ✅ Phase 1 (Completed / Current)
- Login
- Registration
- Add income
- Add expense
- View transactions
- Balance calculation

## 🔄 Phase 2 (Planned)
- Budget tracking
- Notifications
- Monthly reports
- Expense alerts

## 🚀 Phase 3 (Future Scope)
- Charts & analytics
- AI insights
- Bank synchronization
- Family/shared accounts

---

# 🧩 Modules

## User Module
- Register account
- Login
- Manage profile

## Income Module
- Add income
- View income records

## Expense Module
- Add expense
- Categorize expenses

## Transaction Module
- View all transactions
- Filter by category/date

## Report Module
- Generate reports
- Calculate summaries

---

# 🗂️ Database Design

## User Table

| Field | Type |
|------|------|
| id | Long |
| name | String |
| email | String |
| password | String (Hashed) |

---

## Transaction Table

| Field | Type |
|------|------|
| id | Long |
| user_id | Long (FK) |
| type | Enum (INCOME, EXPENSE) |
| amount | Double |
| date | Long |
| category | String |
| description | String |

---

# 📦 Model Classes

## User
```java
class User {
    Long id;
    String name;
    String email;
    String password;
}
