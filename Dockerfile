FROM maven:3.9.7-amazoncorretto-17 AS build

WORKDIR /app
COPY pom.xml .
RUN mvn dependency:go-offline

COPY src ./src
RUN mvn clean package -DskipTests

FROM amazoncorretto:17-alpine-jdk

COPY --from=build /app/target/seller-api-0.0.1-SNAPSHOT.jar /app/app.jar

WORKDIR /app
EXPOSE 8080

CMD ["java", "-jar", "app.jar"]