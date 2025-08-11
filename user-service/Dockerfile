# Folosim o imagine de bază ușoară cu OpenJDK 17
FROM openjdk:17-jdk-slim

# Setăm directorul de lucru în interiorul containerului
WORKDIR /app

# Copiem fișierul JAR construit din `target/`
# Asigură-te că numele fișierului JAR este corect
COPY target/user-service-0.0.1-SNAPSHOT.jar app.jar

# Expunem portul pe care rulează aplicația (portul 3001)
EXPOSE 8081

# Comanda care rulează aplicația atunci când pornește containerul
ENTRYPOINT ["java", "-jar", "app.jar","--server.port=8081", "--eureka.client.service-url.defaultZone=http://eureka-server:8761/eureka"]
