FROM openjdk:17
ADD build/libs/liquibase_users1-0.0.1-SNAPSHOT.jar app.jar
EXPOSE 8081
ENTRYPOINT ["java", "-jar", "app.jar"]