FROM openjdk:26-oraclelinux9
LABEL authors="valkyrie"
ADD target/authentication.jar authentication.jar
ENTRYPOINT ["java", "-jar", "/authentication.jar"]