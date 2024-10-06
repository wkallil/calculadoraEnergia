# Use a imagem base com JDK 17
FROM eclipse-temurin:17-jdk

# Define o diretório de trabalho
WORKDIR /app

# Copia o Maven wrapper (mvnw) e os arquivos de configuração
COPY .mvn/ .mvn
COPY mvnw pom.xml /app/

# Dá permissão de execução para o Maven wrapper
RUN chmod +x ./mvnw

# Baixa as dependências do Maven sem compilar o projeto (fase de dependencies resolve)
RUN ./mvnw dependency:go-offline

# Copia todo o conteúdo do projeto para o diretório de trabalho
COPY . /app

# Executa o comando Maven para compilar e empacotar a aplicação (comando específico para build)
RUN ./mvnw clean package -DskipTests

# Exponha a porta em que a aplicação será executada
EXPOSE 8080

# Define o comando de inicialização da aplicação Spring Boot
CMD ["java", "-jar", "target/calculadoraEnergia.jar"]