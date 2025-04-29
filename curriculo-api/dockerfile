# Usa uma imagem base oficial do OpenJDK 21 (compatível com pom.xml)
FROM openjdk:21-jdk-slim

# Define o diretório de trabalho dentro do container
WORKDIR /app

# Copia o wrapper do Maven e o pom.xml para o container
# Copia separadamente para aproveitar o cache do Docker se as dependências não mudarem
COPY mvnw .
COPY .mvn/ .mvn/
COPY pom.xml .

# Baixa as dependências do projeto (fica em cache se o pom.xml não mudar)
# O "|| true" é uma precaução caso o download falhe temporariamente em algumas redes
RUN ./mvnw dependency:go-offline -B || true

# Copia o código fonte da aplicação para o container
COPY src/ ./src/

# Compila a aplicação e empacota em um JAR executável
# A opção -DskipTests pula a execução dos testes durante o build
RUN ./mvnw package -DskipTests

# Expõe a porta que a aplicação Spring Boot usa (definida no application.properties)
EXPOSE 8080

# Comando para executar a aplicação quando o container iniciar
# Confere se o nome do JAR está correto conforme seu pom.xml (artifactId-version.jar)
ENTRYPOINT ["java", "-jar", "/app/target/curriculo-api-0.0.1-SNAPSHOT.jar"]