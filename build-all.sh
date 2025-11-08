#!/bin/bash

echo "======================================"
echo "Building Microservices Architecture"
echo "======================================"

# Build parent project
echo "Building parent project..."
mvn clean install -DskipTests

# Build individual services
echo "Building Discovery Service..."
cd discovery-service
mvn clean package -DskipTests
cd ..

echo "Building Config Service..."
cd config-service
mvn clean package -DskipTests
cd ..

echo "Building Gateway Service..."
cd gateway-service
mvn clean package -DskipTests
cd ..

echo "Building Keynote Service..."
cd keynote-service
mvn clean package -DskipTests
cd ..

echo "Building Conference Service..."
cd conference-service
mvn clean package -DskipTests
cd ..

echo "======================================"
echo "Build Complete!"
echo "======================================"
echo "To start all services, run:"
echo "docker-compose up -d"
