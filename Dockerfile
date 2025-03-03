# Usa Java 21 en lugar de Java 17
FROM openjdk:21-jdk-slim

# Define el nombre del JAR correcto
ARG JAR_FILE=target/expense-tracker-0.0.1-SNAPSHOT.jar

# Copia el JAR en el contenedor con un nombre más simple
COPY ${JAR_FILE} app.jar

# Expone el puerto en el que corre Spring Boot
EXPOSE 8080

# Comando de entrada para ejecutar la aplicación
ENTRYPOINT ["java", "-jar", "app.jar"]
