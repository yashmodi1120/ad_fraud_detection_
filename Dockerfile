FROM openjdk:20

WORKDIR /Users/yashwardhan.modi/Downloads/Assignment_2

COPY target/app.jar app.jar

EXPOSE 8080

CMD ["java", "-jar", "app.jar"]
