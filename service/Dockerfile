FROM openjdk:26-oraclelinux9
LABEL authors="valkyrie"
ADD target/service.jar service.jar
ENTRYPOINT ["java", "-jar", "/service.jar"]