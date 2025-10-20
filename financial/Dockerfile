FROM openjdk:26-oraclelinux9
LABEL authors="valkyrie"
ADD target/financial.jar financial.jar
ENTRYPOINT ["java", "-jar", "/financial.jar"]