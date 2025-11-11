#!/bin/bash

echo "Starting Payment Client and Booking Portal..."

# Build and start services
docker compose up --build -d

echo "Services starting..."
echo "Waiting for services to be ready..."

# Wait for services to start
sleep 10

echo ""
echo "=== Services Status ==="
docker compose ps

echo ""
echo "=== Access URLs ==="
echo "Payment Client Dashboard: http://localhost:8080"
echo "Booking Portal: http://localhost:9292"
echo "Create Payment: http://localhost:9292/payment"

echo ""
echo "=== Testing API ==="
echo "Testing booking portal API..."
curl -s http://localhost:9292/api/bookings | head -c 100
echo "..."

echo ""
echo "=== Logs ==="
echo "To view logs: docker compose logs -f"
echo "To stop: docker compose down"