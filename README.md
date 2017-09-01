## query-service

Query persons and route orders 

Two KafkaConsumers listen to Party & Route topics and update relational database.  

## Installation

./build-run.sh

## REST

GET  http://<host>:<port>//query-service/resources/query/persons
GET  http://<host>:<port>//query-service/resources/query/routes

