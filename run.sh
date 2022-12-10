#!/bin/bash

echo "Running apps"
nohup java -jar service-discovery/target/*.jar --spring.profiles.active=dev > target/service-discovery.log 2>&1 &
echo "Waiting for the Discovery Service to start"
sleep 20
nohub java -jar product-service/target/*.jar --spring.profiles.active=dev > target/product-service.log 2>&1 &
echo "Waiting for services to start"
sleep 30
