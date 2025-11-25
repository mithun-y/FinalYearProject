Biometric Fingerprint Payment System

Overview
The Biometric Fingerprint Payment System is a secure, modern payment platform that leverages fingerprint authentication combined with PIN security and One-Time Transaction Tokens (OTTT). Built using Spring Boot, PostgreSQL, and AES encryption, this system ensures safe, convenient, and fully authenticated transactions without relying on traditional cards or cash.
This project demonstrates a complete biometric-based financial transaction system with features such as user registration, fingerprint storage, secure authentication, transaction processing, and email notifications.

Features
1. User Registration & Authentication
Users can register with personal details, PIN, and fingerprint.
Fingerprint are AES-encrypted before storage in the database.
Unique account numbers are auto-generated upon registration.
PINs are securely hashed before storage.
Users authenticate using fingerprint + PIN for transactions.

2. Fingerprint Matching
Integration with Source AFIS (Automated Fingerprint Identification System) for accurate fingerprint verification.
Ensures high security and fraud prevention during authentication.

3. Transaction Service
Users can perform debit transactions after generating a One-Time Transaction Token (OTTT).
The OTTT is valid for a single transaction, ensuring enhanced security.
Maintains real-time balance updates in the database.
Supports transaction history and logs for auditing.

4. One-Time Transaction Token (OTTT)
Each transaction requires a unique, server-generated token.
Token ensures that each transaction is authorized only once.
Prevents replay attacks and unauthorized transactions.

5. Email Notification
Spring Mail Service sends transaction confirmation emails to users.
Emails include transaction details: amount debited, date/time, remaining balance.

6. Security Features
Fingerprints are AES-encrypted before storage.
PINs are securely hashed.
Sensitive information (PIN, fingerprint, transaction token) is never exposed to clients.
Supports secure authentication and authorization mechanisms.

7. Technology Stack
Backend: Spring Boot
Database: PostgreSQL
Security: AES encryption for fingerprint, hashed PINs
Email: Spring Mail Service for transaction notifications

Fingerprint Matching: Source AFIS
Other: Maven for dependency management, REST APIs


How It Works

User Registration: Users register with fingerprint, email, and PIN. Fingerprints are AES-encrypted; PINs are hashed. Account numbers are auto-generated.
Authentication: Users authenticate using fingerprint + PIN. Source AFIS verifies fingerprints.
Transaction Authorization: Users request a One-Time Transaction Token (OTTT).
Debit Transaction: Users initiate a transaction using the OTTT. Once validated, the transaction is processed, and the balance updated.
Notification: Users receive an email confirmation with transaction details.
Security: Sensitive data is encrypted or hashed to ensure maximum security.
