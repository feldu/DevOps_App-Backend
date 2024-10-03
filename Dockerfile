#FROM amazoncorretto:11-alpine
#WORKDIR /service
#COPY target/app-0.0.1-SNAPSHOT.jar /service/app-0.0.1-SNAPSHOT.jar
#CMD ["java", "-jar", "app-0.0.1-SNAPSHOT.jar"]

FROM maven:3.9.4-amazoncorretto-11 AS build

WORKDIR /app

COPY pom.xml ./
RUN mvn dependency:go-offline -B

COPY src ./src

RUN mvn clean package -DskipTests

FROM amazoncorretto:11-alpine

WORKDIR /service

COPY --from=build /app/target/app-0.0.1-SNAPSHOT.jar /service/app-0.0.1-SNAPSHOT.jar

CMD ["java", "-jar", "app-0.0.1-SNAPSHOT.jar"]
