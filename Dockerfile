# Use a imagem base do Java
FROM eclipse-temurin:17-jdk-jammy

# Configure o diretório de trabalho
WORKDIR /app

# Copie o projeto para dentro da imagem
COPY . /app

# Execute o comando de build (assumindo que você usa Maven)
RUN ./mvnw clean package

# Especifique o comando para iniciar o servidor
CMD ["java", "-jar", "target/GlobalSolution-1.0-SNAPSHOT.jar"]
