## query-service

Query persons and route orders 

Two KafkaConsumers listen to Party & Route topics and update relational database.  

## Installation

- run kafka cluster
- run mssql 
- if not already done, connect with a client to mssql
  jdbc:sqlserver://localhost:1433
  credentials see in mssql.centos

  and create Demo database

./build-run.sh

## REST

GET  http://<host>:<port>//query-service/resources/query/persons
GET  http://<host>:<port>//query-service/resources/query/routes

