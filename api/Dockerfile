FROM openjdk:26-oraclelinux9
LABEL authors="valkyrie"
ADD target/api.jar api.jar
ENTRYPOINT ["java", "-jar", "/api.jar"]