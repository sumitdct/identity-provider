# The Docker file
FROM openjdk:8-jdk-alpine
MAINTAINER SUMIT CHOUKSEY "sumitchouksey2315@gmail.com"
ADD /target/identity-provider.jar identity-provider.jar
EXPOSE 8080
ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","/identity-provider.jar"]
