# Etapa 1: compilar el proyecto con Maven
FROM maven:3.9-eclipse-temurin-21 AS build
WORKDIR /app
COPY pom.xml .
COPY src ./src
RUN mvn clean package -DskipTests

# Etapa 2: empaquetar el .war en la imagen de Wildfly
FROM quay.io/wildfly/wildfly:latest
COPY --from=build /app/target/ciudadano.war /opt/jboss/wildfly/standalone/deployments/
EXPOSE 8080
CMD ["/opt/jboss/wildfly/bin/standalone.sh", "-b", "0.0.0.0"]
