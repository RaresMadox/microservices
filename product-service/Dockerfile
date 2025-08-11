# Folosim o imagine de bază ușoară cu OpenJDK 17
FROM openjdk:17-jdk-slim

# Setăm directorul de lucru în interiorul containerului
WORKDIR /app

# Copiem fișierul JAR construit din `target/` în container
# Verifică numele fișierului JAR generat în directorul `target`.
COPY target/product-service-0.0.1-SNAPSHOT.jar app.jar

# Expunem portul pe care rulează serviciul (portul 3002)
EXPOSE 8082

# Comanda care rulează aplicația atunci când pornește containerul
# Setăm variabilele de mediu pentru a permite comunicarea între containere
ENTRYPOINT ["java", "-jar", "app.jar", "--server.port=8082", "--eureka.client.service-url.defaultZone=http://eureka-server:8761/eureka", "--user.service.url=http://user-service:8081"]

