FROM openjdk:24-jdk-slim
WORKDIR /app
COPY target/accreditation-service-1.0.0-SNAPSHOT.jar accreditation-service.jar
EXPOSE 9999
ENTRYPOINT ["java", "-jar", "accreditation-service.jar"]
