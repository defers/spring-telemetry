package com.defers.springtelemetry.infra.kafka;

import com.defers.springtelemetry.domain.user.port.out.TopicMessageSender;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

@Service
public class KafkaMessageSender implements TopicMessageSender {
    private static final Logger log = LoggerFactory.getLogger(KafkaMessageSender.class);
    private final KafkaTemplate<Object, Object> kafkaTemplate;
    private final String topic;

    public KafkaMessageSender(KafkaTemplate<Object, Object> kafkaTemplate, KafkaProperties kafkaProperties) {
        this.kafkaTemplate = kafkaTemplate;
        Assert.notNull(kafkaProperties, "Kafka properties can not be null");
        this.topic = kafkaProperties.topics().inbound();
    }

    @Override
    public <T> void send(T msg) {
        log.info("Sending message: {} to kafka topic: {}", msg, topic);
        var result = kafkaTemplate.send(topic, msg);
        result.whenComplete((objectObjectSendResult, throwable) -> {
            if (throwable == null) {
                log.info(
                        "Sent message= {} with offset= {}",
                        msg,
                        objectObjectSendResult.getRecordMetadata().offset());
            } else {
                log.error("Unable to send message to kafka topic, {}", throwable.getMessage(), throwable);
            }
        });
    }
}
