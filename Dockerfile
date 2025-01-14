FROM openjdk:17-jdk

WORKDIR /app

COPY target/seller-api-0.0.1-SNAPSHOT.jar /app/seller-api-0.0.1-SNAPSHOT.jar

EXPOSE 8080

CMD ["java", "-jar", "seller-api-0.0.1-SNAPSHOT.jar"]