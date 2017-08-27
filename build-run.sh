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
  adesso/query-service:${VERSION}

docker logs -f query-service
