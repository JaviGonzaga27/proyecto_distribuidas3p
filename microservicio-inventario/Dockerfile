FROM eclipse-temurin:21-jdk-alpine as builder

WORKDIR /app
COPY target/*.jar app.jar
RUN java -Djarmode=layertools -jar app.jar extract

FROM eclipse-temurin:21-jre-alpine

RUN addgroup -g 1001 -S spring && adduser -S spring -G spring -u 1001
USER 1001:1001

WORKDIR /app

COPY --from=builder --chown=1001:1001 app/dependencies/ ./
COPY --from=builder --chown=1001:1001 app/spring-boot-loader/ ./
COPY --from=builder --chown=1001:1001 app/snapshot-dependencies/ ./
COPY --from=builder --chown=1001:1001 app/application/ ./

# Variables de entorno con valores por defecto
ENV SPRING_PROFILES_ACTIVE=${SPRING_PROFILES_ACTIVE:-production}
ENV DB_HOST=${DB_HOST:-localhost}
ENV RABBITMQ_HOST=${RABBITMQ_HOST:-localhost}

EXPOSE 8081

HEALTHCHECK --interval=30s --timeout=10s --start-period=60s --retries=3 \
  CMD curl -f http://localhost:8081/actuator/health || exit 1

ENTRYPOINT ["java", "org.springframework.boot.loader.JarLauncher"]