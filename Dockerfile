# Usar a imagem base do Java com Maven instalado
FROM maven:3.8.6-eclipse-temurin-17

# Criar e definir o diretório de trabalho
WORKDIR /app

# Copiar o código do projeto para dentro da imagem
COPY . /app

# Executar o comando de build usando Maven
RUN mvn clean package

# Expor a porta usada pela aplicação
EXPOSE 8080

# Comando para iniciar o servidor
CMD ["java", "-jar", "target/GlobalSolution-1.0-SNAPSHOT.jar"]
