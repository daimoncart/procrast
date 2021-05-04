FROM openjdk:11
EXPOSE 8083
ADD target/procrast-docker-jenkins-integration.jar procrast-docker-jenkins-integration.jar
ENTRYPOINT ["java", "-jar", "procrast-docker-jenkins-integration.jar"]