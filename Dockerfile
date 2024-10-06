# Etapa de build
FROM maven:3.9.7-amazoncorretto-17 AS build

# Copiar o código fonte e o arquivo pom.xml
COPY src /app/src
COPY pom.xml /app

# Definir o diretório de trabalho
WORKDIR /app

# Rodar o comando de build do Maven
RUN mvn clean install -DskipTests

# Etapa de runtime
FROM amazoncorretto:17-alpine-jdk

# Copiar o JAR gerado da etapa de build
COPY --from=build /app/target/agenda-0.0.1-SNAPSHOT.jar /app/app.jar

# Definir o diretório de trabalho
WORKDIR /app

# Expor a porta 8080
EXPOSE 8080

# Definir o comando de inicialização da aplicação
CMD ["java", "-jar", "app.jar"]