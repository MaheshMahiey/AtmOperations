
# ATM CRUD (Spring Boot)

A minimal Spring Boot REST API for basic ATM operations: **deposit**, **withdraw**, **PIN change**, and **statement**. Uses **H2 in-memory DB**, **Spring Data JPA**, and **BCrypt** for PIN hashing.

## Requirements
- JDK 17+
- Maven 3.9+

## Quick Start
```bash
mvn spring-boot:run
```
Seeds 2 demo accounts at startup:
- Account `11112222`, PIN `1234`, Balance `10000.00`
- Account `33334444`, PIN `4321`, Balance `5000.00`

H2 console: `http://localhost:8080/h2-console` (JDBC URL: `jdbc:h2:mem:atmdb`)

## API
Base URL: `http://localhost:8080/api/atm`

### Deposit
`POST /deposit`
```json
{
  "accountNumber": "11112222",
  "pin": "1234",
  "amount": 250.75
}
```
**Response**
```json
{
  "accountNumber": "11112222",
  "balance": 10250.75
}
```

### Withdraw
`POST /withdraw`
```json
{
  "accountNumber": "11112222",
  "pin": "1234",
  "amount": 100.00
}
```

### Change PIN
`POST /pin/change`
```json
{
  "accountNumber": "11112222",
  "oldPin": "1234",
  "newPin": "7890"
}
```

### Statement
`POST /statement`
```json
{
  "accountNumber": "11112222",
  "pin": "1234",
  "from": "2025-01-01T00:00:00Z",
  "to": "2025-12-31T23:59:59Z"
}
```
`from`/`to` optional — defaults to last 30 days.

## Sample cURL
```bash
# Deposit
curl -s -X POST http://localhost:8080/api/atm/deposit   -H 'Content-Type: application/json'   -d '{"accountNumber":"11112222","pin":"1234","amount":1000}' | jq

# Withdraw
curl -s -X POST http://localhost:8080/api/atm/withdraw   -H 'Content-Type: application/json'   -d '{"accountNumber":"11112222","pin":"1234","amount":200}' | jq

# Change PIN
curl -s -X POST http://localhost:8080/api/atm/pin/change   -H 'Content-Type: application/json'   -d '{"accountNumber":"11112222","oldPin":"1234","newPin":"5678"}' | jq

# Statement (last 30 days)
curl -s -X POST http://localhost:8080/api/atm/statement   -H 'Content-Type: application/json'   -d '{"accountNumber":"11112222","pin":"5678"}' | jq
```

## Notes
- This is a simple demo; real ATM systems require stronger security, audit, rate limiting, and compliance.
- No Spring Security login; each request verifies account + PIN against a BCrypt hash.
- Monetary values use `BigDecimal` with 2 decimal places.
