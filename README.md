# Project with Spring Boot example with hexagonal architecture and OpenTelemetry

swagger ui: http://localhost:8080/swagger-ui/index.html

## Run application

From shell:

```shell
java -jar spring-telemetry-<version-number>.jar
```

For running infrastructure:
```shell
docker compose up -d
```

OpenTelemetry javaagent config:

```shell
-javaagent:opentelemetry-javaagent.jar
-Dotel.traces.exporter=otlp
-Dotel.logs.exporter=none
-Dotel.metrics.exporter=none
-Dotel.exporter.jaeger.endpoint="http://localhost:14250"
-Dotel.exporter.otlp.traces.endpoint="http://localhost:4318/v1/traces"
-Dotel.service.name=spring-agent
-Dotel.resource.attributes="resource.attribute1=value1,resource.attribute2=value2"
```
