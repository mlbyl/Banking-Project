# Banking-App


## Project Overview

Banking-App is a simple banking application built with Java (SPRING BOOT).  
The project simulates basic banking operations for learning and testing purposes.

>Note: Project is built during the learning process, so the code structure of domains evolves differently.

## Features

Banking-App currently implements the following functionalities:

## 1. User Registration & Login

- Users can **register** with:
  - Full name (name + surname)
  - Date of birth
  - Unique email address
  - Secure password (hashed with Spring Security’s `PasswordEncoder`)
  - Unique phone number  

- During registration:
  - The system checks if the email already exists.
  - Passwords are hashed before being saved in the database.
  - New users are assigned the default role `ROLE_USER`.
  - User accounts are initially created with status **INACTIVE** until first login.

- **Login process:**
  - Users authenticate with their email and password.
  - The `AuthenticationManager` validates credentials.
  - On successful login:
    - A **JWT token** is generated and returned to the client.
    - The token includes `userId` and other claims for authorization.
    - User’s `lastLogin` is updated.
    - User status is set to **ACTIVE** automatically.
  - On failed login, meaningful error messages are returned (`USER_NOT_FOUND`, `INVALID_CREDENTIALS`, etc.).

- **User profile** contains:
  - Full name
  - Date of birth
  - Phone number
  - Email address
  - Role (`ROLE_USER` or `ROLE_ADMIN`)
  - Account status (`ACTIVE`, `INACTIVE`, `SUSPENDED`, `BANNED`)
  - Last login date
  - Accounts and transactions (linked via relationships in the database)

- **Future updates:**
  - Refresh tokens for session management.
  - Email verification during registration.
  - Admin endpoints for managing user roles and statuses.


### 2. Bank Account Management

- Users can create multiple bank accounts linked to their profile.
- Each account has:
  - **Account Number**: A unique 8-character long value, automatically generated.
  - **IBAN**: A 16-character value where:
    - The first 8 characters are a constant development-based bank code (configurable later).
    - The last 8 characters are derived from the account number.
- Accounts support different **Types** (`SAVINGS`, `CHECKING`, `CREDIT`, `LOAN`) defined by an enum.
- Accounts maintain a **Status** (`ACTIVE`, `INACTIVE`, `SUSPENDED`, `CLOSED`, `FROZEN`, `PENDING_APPROVAL`, `DORMANT`) defined by an enum.  
  - By default, newly created accounts are set to `PENDING_APPROVAL`.
  - Status is validated before update and delete operations.  
- Accounts are associated with a **Currency** enum (to be extended with a central bank integration in the future).
- Each account has a **balance** (default: 0.00) stored with high precision (`BigDecimal`).
- Users can view their account details, including balance, account type, status, and currency.
- Accounts have a **lastActivityDate** field that updates whenever the account is modified or deleted, and can be used for automated status transitions (e.g., setting to `DORMANT` after inactivity).
- Accounts are linked to a **User** (one-to-many relationship).
- Accounts are also connected to **Transactions**:
  - `sentTransactions`: All outgoing transfers.
  - `receivedTransactions`: All incoming transfers.
- Security checks ensure:
  - Only the owner of an account can view, update, or delete it.
  - Only `ACTIVE` accounts can be updated.
  - Only `ACTIVE` or `INACTIVE` accounts can be closed (soft-delete by setting status to `CLOSED`).


## 3. Money Transfers
- Users can transfer money between their own accounts or to other users.  
- Transfers require both the sender and receiver accounts to be **ACTIVE**.  
- Validation ensures the sender has sufficient balance before the transfer is processed.  
- Transfers are not allowed to the same account.  
- Both sender and receiver accounts update their `lastActivityDate` upon transfer.  
- All transfers are logged in the `transactions` table with details of sender, receiver, amount, and status.  
- **Future update**: Real-time notifications will be sent to users for successful transfers.  

## 4. Balance Management
- Users can view their account balance at any time.  
- Balance updates are immediate after:  
  - Deposits (crediting the account)  
  - Withdrawals (debiting the account)  
  - Transfers (debit sender, credit receiver)  
- Withdrawals are only allowed from **CHECKING** or **SAVINGS** accounts.  
- Withdrawals and deposits are validated to ensure the transaction amount is greater than zero.  
- Transaction history can be retrieved by `userId` or `accountId`.  


## 5. Payments
- Users can make payments directly from their bank accounts.  
- Payments are processed as **transactions** and stored in the transaction history for full accountability.  
- Currently, payments are a specialization of transfers (from user’s account to a system or merchant account).  
- Payment types can be extended in the future to include:  
  - Loan installments  


## 6. Error Handling & Validation

Banking-App uses a **centralized and standardized approach** for handling errors and validating user input. All API responses conform to a unified structure using the `Result<T>` class, and exceptions extend `BaseException` for consistency.

---

#### Standardized API Responses

All API responses follow a predictable structure:

- `success` (boolean): Indicates whether the operation was successful.
- `data` (generic type): Contains the response payload if available.
- `message` (String): Human-readable message.
- `errorCode` (String, optional): Standardized error code for failures.
- `statusCode` (Integer, optional): HTTP status code for failures.

## 7. Technologies

- **Backend:** Java 17, Spring Boot 3
- **Database:** PostgreSQL
- **Lombok** — reduces boilerplate code
- **Spring Data JPA** — ORM framework
- **Spring Security** — user authentication


---



