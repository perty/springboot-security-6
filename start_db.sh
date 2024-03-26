#!/usr/bin/env bash

# Start the database. If there is no existing database, create a new one. Note the port number is 5437.

docker start spring-security || \
docker run --name spring-security -e POSTGRES_PASSWORD=mysecret -d -p 5437:5432 postgres:16
