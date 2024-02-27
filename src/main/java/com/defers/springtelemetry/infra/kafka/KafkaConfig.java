package com.defers.springtelemetry.infra.kafka;

import io.micrometer.common.KeyValues;
import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.config.TopicBuilder;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;
import org.springframework.kafka.support.micrometer.KafkaRecordSenderContext;
import org.springframework.kafka.support.micrometer.KafkaTemplateObservationConvention;

@Configuration
public class KafkaConfig {
    @Bean
    public NewTopic infoTopic() {
        return TopicBuilder.name("USER_IN").partitions(3).replicas(2).build();
    }

    @Bean
    public KafkaTemplate<Object, Object> kafkaTemplate(ProducerFactory<Object, Object> producerFactory) {
        KafkaTemplate<Object, Object> template = new KafkaTemplate<>(producerFactory);
        template.setObservationEnabled(true);
        template.setObservationConvention(new KafkaTemplateObservationConvention() {
            @Override
            public KeyValues getLowCardinalityKeyValues(KafkaRecordSenderContext context) {
                return KeyValues.of(
                        "topic",
                        context.getDestination(),
                        "id",
                        String.valueOf(context.getRecord().key()));
            }
        });
        return template;
    }

    @Bean
    public ConcurrentKafkaListenerContainerFactory<Object, Object> listenerFactory(
            ConsumerFactory<Object, Object> consumerFactory) {
        ConcurrentKafkaListenerContainerFactory<Object, Object> factory =
                new ConcurrentKafkaListenerContainerFactory<>();
        factory.getContainerProperties().setObservationEnabled(true);
        factory.setConsumerFactory(consumerFactory);
        return factory;
    }
}
