FROM openjdk:8-jdk-alpine
ADD target/pizza.authentication-0.0.1-SNAPSHOT.jar docker-pizza-authentication.jar
EXPOSE 9091
EXPOSE 27011
ENTRYPOINT ["java","-jar","docker-pizza-authentication.jar"]
