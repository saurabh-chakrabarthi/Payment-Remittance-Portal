# Payment Client Application

A Spring Boot application that provides quality checks and validation for payment bookings from the booking portal server.

## Features

- Payment validation (email, duplicates, amount thresholds)
- Over/under payment detection
- Fee calculation based on payment amount
- Dashboard with payment statistics
- Quality check reporting

## Requirements

- Docker & Docker Compose
- Java 17+ (for local development)
- Maven 3.6+ (for local development)

## Quick Start with Docker

### 1. Start both client and server:
```bash
docker-compose up --build
```

### 2. Access the applications:
- **Payment Client Dashboard**: http://localhost:8080
- **Booking Portal**: http://localhost:9292
- **Booking Form**: http://localhost:9292/payment

## Local Development

### 1. Start the server first:
```bash
cd ../server
docker-compose up
```

### 2. Run the client locally:
```bash
mvn spring-boot:run
```

### 3. Access at http://localhost:8080

## API Configuration

The client connects to the booking portal server via:
- **Docker**: `http://booking-portal:9292`
- **Local**: `http://localhost:9292`

## Testing End-to-End

1. Start both services with `docker-compose up --build`
2. Create payments at http://localhost:9292/payment
3. View quality checks at http://localhost:8080
4. Check API: `curl http://localhost:9292/api/bookings`

## Quality Check Rules

- **Invalid Email**: Email format validation
- **Duplicate Payment**: Same email, different reference
- **Amount Threshold**: Payments > $1,000,000
- **Over/Under Payment**: Amount vs amount received mismatch

## Fee Structure

- < $1,000: 5% fee
- $1,000 - $10,000: 3% fee  
- > $10,000: 2% fee