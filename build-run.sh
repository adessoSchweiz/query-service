#!/usr/bin/env bash

cd $(dirname $0)

VERSION=1.0.0
echo VERSION: $VERSION

mvn clean install

docker stop query-service
docker rm query-service

docker build -t adesso/query-service:${VERSION} .


docker run -d \
  --name query-service \
  --net=hackathon \
  -p 8093:8080 \
  -e DB_HOST="172.17.0.1" \
  -e DB_PORT="1433" \
  -e WILDFLY_USER="admin" \
  -e WILDFLY_PASSWORD="admin" \
  -e JNDI_NAME="java:jboss/jdbc/demo" \
  -e DATASOURCE_NAME="Demo" \
  -e DATABASE_NAME="Demo" \
  -e DATABASE_USER="sa" \
  -e DATABASE_PASSWORD="adesso@OpenShift" \
 adesso/query-service:${VERSION}


docker logs -f query-service