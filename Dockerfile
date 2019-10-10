FROM openjdk:8
ADD target/notification-microservice.jar notification-microservice.jar
EXPOSE 8083
ENTRYPOINT ["java", "-jar", "notification-microservice.jar"]