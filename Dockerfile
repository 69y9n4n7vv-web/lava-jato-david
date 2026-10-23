# Etapa 1: Build compilando sem o repackage falhar
FROM maven:3.9-eclipse-temurin-17 AS build
WORKDIR /app
COPY . .
RUN mvn clean package -Dspring-boot.repackage.skip=true -DskipTests

# Etapa 2: Executar as classes compiladas diretamente
FROM eclipse-temurin:17-jdk-alpine
WORKDIR /app
COPY --from=build /app/target/classes /app/classes
COPY --from=build /root/.m2/repository /root/.m2/repository
COPY --from=build /app/target/*.jar /app/app.jar

EXPOSE 8080
CMD ["java", "-cp", "/app/classes:/app/app.jar", "com.lavajato.LavaJatoApplication"]
