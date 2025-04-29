FROM openjdk:17-jdk-slim

WORKDIR /app

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
ENTRYPOINT ["java", "-jar", "/app/target/curriculo-api-0.0.1-SNAPSHOT.jar"]