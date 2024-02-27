package com.defers.springtelemetry.domain.user.port.out;

public interface QueueMessageSender {
    <T> void send(T msg);
}
