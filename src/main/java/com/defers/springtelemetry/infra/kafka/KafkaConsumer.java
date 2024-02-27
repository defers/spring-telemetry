package com.defers.springtelemetry.infra.kafka;

import com.defers.springtelemetry.domain.user.model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.stereotype.Component;

@Component
public class KafkaConsumer {
    private static final Logger log = LoggerFactory.getLogger(KafkaConsumer.class);

    @KafkaListener(topics = "${app.kafka.topics.inbound}", groupId = "group1", containerFactory = "listenerFactory")
    void consume(User user, @Header(name = "traceparent") String traceParent) {
        log.info("Consumed kafka message with body: {}, traceParent: {}", user, traceParent);
    }
}
