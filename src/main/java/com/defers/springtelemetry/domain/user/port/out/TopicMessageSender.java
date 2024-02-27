package com.defers.springtelemetry.domain.user.port.out;

public interface TopicMessageSender {
    <T> void send(T msg);
}
