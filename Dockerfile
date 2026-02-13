FROM eclipse-temurin:20-jdk AS build
WORKDIR /app

COPY .mvn .mvn
COPY mvnw pom.xml ./
RUN chmod +x mvnw && ./mvnw -DskipTests dependency:go-offline

COPY src src
RUN ./mvnw -DskipTests package && cp target/*.jar /app/app.jar

FROM eclipse-temurin:20-jre
WORKDIR /app

COPY --from=build /app/app.jar app.jar

EXPOSE 8082
ENTRYPOINT ["java", "-jar", "app.jar"]
