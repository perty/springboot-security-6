#!/usr/bin/env bash
docker start spring-security || \
docker run --name spring-security -e POSTGRES_PASSWORD=mysecret -d -p 5437:5432 postgres:16
