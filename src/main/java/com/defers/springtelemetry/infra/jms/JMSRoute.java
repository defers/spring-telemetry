package com.defers.springtelemetry.infra.jms;

import com.defers.springtelemetry.domain.user.model.User;
import com.defers.springtelemetry.infra.rest.ServiceRestConnector;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.stereotype.Component;

@Component
public class JMSRoute {
    private static final Logger log = LoggerFactory.getLogger(JMSRoute.class);
    private final ServiceRestConnector serviceRestConnector;

    public JMSRoute(ServiceRestConnector serviceRestConnector) {
        this.serviceRestConnector = serviceRestConnector;
    }

    @JmsListener(destination = "${app.jms.queues.inbound}")
    public void consume(
            User user,
            @Header("typeId") String typeId,
            @Header(value = "traceparent", required = false) String traceParentId) {
        log.info(
                "Consumed message from queue, body: {}, header typeId: {}, header traceParentId: {}",
                user,
                typeId,
                traceParentId);
        serviceRestConnector.create(user);
    }
}
