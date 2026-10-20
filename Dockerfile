# --- Multi-stage build for Spring Boot on Render ---

# Etapa 1: Build de la aplicación con Maven y Eclipse Temurin JDK 25
FROM eclipse-temurin:25-jdk AS build
WORKDIR /app

# Copiar el wrapper de maven y el pom.xml primero para aprovechar el cache de Docker
COPY .mvn/ .mvn
COPY mvnw pom.xml ./

# Dar permisos de ejecución al wrapper
RUN chmod +x mvnw

# Descargar dependencias
RUN ./mvnw dependency:go-offline -B || true

# Copiar el código fuente y compilar el JAR (sin ejecutar tests en el build del contenedor)
COPY src ./src
RUN ./mvnw clean package -DskipTests

# Etapa 2: Imagen ligera para ejecución (JRE 25)
FROM eclipse-temurin:25-jre
WORKDIR /app

# Crear usuario sin privilegios por seguridad
RUN groupadd -r spring && useradd -r -g spring spring
USER spring:spring

# Copiar el JAR generado en la etapa de build
COPY --from=build /app/target/*.jar app.jar

# Render asigna dinámicamente la variable PORT en runtime
ENV PORT=8080
EXPOSE ${PORT}

# Ejecución de la aplicación
ENTRYPOINT ["java", "-jar", "app.jar"]
