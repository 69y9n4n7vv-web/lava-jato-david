FROM eclipse-temurin:17-jdk-alpine
WORKDIR /app
COPY . .
EXPOSE 8080
CMD ["sh", "-c", "java -cp target/classes:target/dependency/* org.springframework.boot.loader.launch.JarLauncher || java -jar target/*.jar || java -cp target/classes com.example.lavajato.LavaJatoApplication"]
