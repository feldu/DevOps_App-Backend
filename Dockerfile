FROM amazoncorretto:11-alpine
WORKDIR /service
EXPOSE 31510
COPY target/app-0.0.1-SNAPSHOT.jar /service/app-0.0.1-SNAPSHOT.jar
CMD ["java", "-jar", "app-0.0.1-SNAPSHOT.jar"]
