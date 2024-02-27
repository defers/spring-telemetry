package com.defers.springtelemetry.infra.jms;

import org.apache.activemq.RedeliveryPolicy;
import org.springframework.boot.autoconfigure.jms.activemq.ActiveMQConnectionFactoryCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jms.support.converter.MappingJackson2MessageConverter;
import org.springframework.util.Assert;

@Configuration
public class JMSConfig {

    @Bean
    public MappingJackson2MessageConverter mappingJackson2MessageConverter() {
        var converter = new MappingJackson2MessageConverter();
        converter.setTypeIdPropertyName("typeId");
        return converter;
    }

    @Bean
    public ActiveMQConnectionFactoryCustomizer configureRedeliveryPolicy(JmsProperties jmsProperties) {
        var redelivery = jmsProperties.redelivery();
        Assert.notNull(redelivery, "Redelivery properties cannot be null");

        return connectionFactory -> {
            RedeliveryPolicy redeliveryPolicy = new RedeliveryPolicy();
            redeliveryPolicy.setUseExponentialBackOff(true);
            redeliveryPolicy.setInitialRedeliveryDelay(redelivery.initialRedeliveryDelay());
            redeliveryPolicy.setBackOffMultiplier(redelivery.backOffMultiplier());
            redeliveryPolicy.setMaximumRedeliveries(redelivery.maximumRedeliveries());
            redeliveryPolicy.setMaximumRedeliveryDelay(redelivery.maximumRedeliveryDelay());
            connectionFactory.setRedeliveryPolicy(redeliveryPolicy);
        };
    }
}
