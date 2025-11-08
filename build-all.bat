@echo off

echo ======================================
echo Building Microservices Architecture
echo ======================================

REM Build parent project
echo Building parent project...
call mvn clean install -DskipTests

REM Build individual services
echo Building Discovery Service...
cd discovery-service
call mvn clean package -DskipTests
cd ..

echo Building Config Service...
cd config-service
call mvn clean package -DskipTests
cd ..

echo Building Gateway Service...
cd gateway-service
call mvn clean package -DskipTests
cd ..

echo Building Keynote Service...
cd keynote-service
call mvn clean package -DskipTests
cd ..

echo Building Conference Service...
cd conference-service
call mvn clean package -DskipTests
cd ..

echo ======================================
echo Build Complete!
echo ======================================
echo To start all services, run:
echo docker-compose up -d

pause
