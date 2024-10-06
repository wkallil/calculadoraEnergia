FROM eclipse-temurin:17-jdk

# Instala o Maven
RUN apt-get update && apt-get install -y maven

# Defina o diretório de trabalho
WORKDIR /app

# Copia o pom.xml para o diretório de trabalho
COPY pom.xml .

# Copia o script mvnw e dá permissão de execução
COPY mvnw /app/mvnw
RUN chmod +x /app/mvnw

# Baixa as dependências do Maven sem compilar o projeto
RUN ./mvnw dependency:go-offline

# Copia todo o conteúdo do projeto para o diretório de trabalho
COPY . .

# Executa o comando Maven para compilar e empacotar a aplicação
RUN ./mvnw clean package -DskipTests

# Exponha a porta em que a aplicação será executada
EXPOSE 8080

# Comando para rodar a aplicação
CMD ["java", "-jar", "target/seu-arquivo.jar"]