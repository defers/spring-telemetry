package com.defers.springtelemetry.infra.kafka;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties("app.kafka")
public record KafkaProperties(Topics topics) {
    public record Topics(String inbound) {}
}
