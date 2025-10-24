FROM eclipse-temurin:21-jdk-jammy as builder
WORKDIR /opt/app
COPY webshop-backend/.mvn/ .mvn
COPY webshop-backend/mvnw webshop-backend/pom.xml ./
RUN chmod +x mvnw
RUN ./mvnw dependency:go-offline
COPY webshop-backend/src ./src
RUN ./mvnw clean install -DskipTests

FROM eclipse-temurin:21-jre-jammy
WORKDIR /opt/app
EXPOSE 8080
COPY --from=builder /opt/app/target/*.jar /opt/app/*.jar
ENTRYPOINT ["java","-Dspring.profiles.active=prod", "-jar", "/opt/app/*.jar" ]