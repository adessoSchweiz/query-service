FROM jboss/wildfly:10.1.0.Final

ENV DEPLOYMENT_DIR ${JBOSS_HOME}/standalone/deployments/

ADD target/query-service.war ${DEPLOYMENT_DIR}

#FROM adesso/wildfly-mssql:1.0.0

#MAINTAINER Robert Brem <robert.brem@adesso.ch>

#ADD target/query-service.war ${JBOSS_HOME}/application.war

#CMD ${JBOSS_HOME}/start.sh