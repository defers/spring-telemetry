package com.defers.springtelemetry.infra.jms;

import com.defers.springtelemetry.domain.user.port.out.QueueMessageSender;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

@Service
public class JmsMessageSender implements QueueMessageSender {
    private static final Logger log = LoggerFactory.getLogger(JmsMessageSender.class);
    private final JmsTemplate jmsTemplate;
    private final String queue;

    public JmsMessageSender(JmsTemplate jmsTemplate, JmsProperties jmsProperties) {
        this.jmsTemplate = jmsTemplate;
        Assert.notNull(jmsProperties, "JMS properties can not be null");
        Assert.notNull(jmsProperties.queues(), "Queues can not be null");
        Assert.notNull(jmsProperties.queues().inbound(), "Inbound queue can not be null");
        this.queue = jmsProperties.queues().inbound();
    }

    @Override
    public <T> void send(T msg) {
        log.info("Send message to queue {}", queue);
        jmsTemplate.convertAndSend(queue, msg);
    }
}
