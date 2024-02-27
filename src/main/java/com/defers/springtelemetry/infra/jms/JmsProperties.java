package com.defers.springtelemetry.infra.jms;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "app.jms")
public record JmsProperties(Queues queues, Redelivery redelivery) {
    public record Queues(String inbound) {}

    public record Redelivery(
            Long initialRedeliveryDelay,
            Double backOffMultiplier,
            Integer maximumRedeliveries,
            Integer maximumRedeliveryDelay) {}
}
