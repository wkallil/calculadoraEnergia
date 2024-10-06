FROM eclipse-temurin:17-jdk

# Defina o diretório de trabalho
WORKDIR /app

# Copia o pom.xml e o script mvnw para o diretório de trabalho
COPY pom.xml .
COPY mvnw ./
COPY .mvn .mvn

# Dê permissão de execução ao script mvnw
RUN chmod +x mvnw
RUN chmod +x ./mvnw && chmod +x ./mvnw.cmd

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