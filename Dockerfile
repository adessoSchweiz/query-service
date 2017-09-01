FROM adesso/wildfly-mssql:1.0.0

MAINTAINER Robert Brem <robert.brem@adesso.ch>

ADD target/query-service.war ${JBOSS_HOME}/application.war

CMD ${JBOSS_HOME}/start.sh