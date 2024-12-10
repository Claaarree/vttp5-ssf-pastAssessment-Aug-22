FROM openjdk:23-jdk
# can also use image below
# eclipse-temurin:23-jdk

LABEL MAINTAINER="Clare"
LABEL description="This is VTTP ssf past assessment Aug 22"
LABEL name="vttp5a-ssf-pastAssessment-Aug22"

ARG APP_DIR=/app

WORKDIR ${APP_DIR}

COPY .mvn .mvn
COPY src src
COPY mvnw .
COPY mvnw.cmd .
COPY pom.xml .

# only need the run step below (the chmod thing) if using eclipse-temurin image...
# if use openjdk then no need... needs to be tested tho... 
RUN chmod a+x ./mvnw && ./mvnw clean package -Dmaven.tests.skip=true

ENV SERVER_PORT=4000

EXPOSE ${SERVER_PORT}

ENTRYPOINT java -jar target/vttp5a-ssf-pastAssessment-Aug-22-0.0.1-SNAPSHOT.jar --dataDir=src/main/resources/data
# Another way to specify entrypoint
# ENTRYPOINT ["java", "-jar", "target/ssf_15l-0.0.1-SNAPSHOT.jar --dataDir=src/main/resources/data"]