package com.defers.springtelemetry.domain.user.usecase;

import com.defers.springtelemetry.domain.user.model.User;
import com.defers.springtelemetry.domain.user.port.in.UserUseCase;
import com.defers.springtelemetry.domain.user.port.out.QueueMessageSender;
import com.defers.springtelemetry.domain.user.port.out.TopicMessageSender;
import com.defers.springtelemetry.domain.user.port.out.UserRepository;
import io.opentelemetry.instrumentation.annotations.SpanAttribute;
import io.opentelemetry.instrumentation.annotations.WithSpan;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.Assert;

public class UserService implements UserUseCase {
    private static final Logger log = LoggerFactory.getLogger(UserService.class);
    private final UserRepository userRepository;
    private final QueueMessageSender queueMessageSender;
    private final TopicMessageSender topicMessageSender;

    public UserService(
            UserRepository userRepository,
            QueueMessageSender queueMessageSender,
            TopicMessageSender topicMessageSender) {
        this.userRepository = userRepository;
        this.queueMessageSender = queueMessageSender;
        this.topicMessageSender = topicMessageSender;
    }

    @Override
    public List<User> findAll() {
        log.info("Service method findAll");
        return userRepository.findAll();
    }

    @Override
    public User create(User user) {
        log.info("Service method create");
        Assert.notNull(user, "User can not be null");
        user.validate();
        return userRepository.create(user);
    }

    @Override
    public User update(int id, User user) {
        Assert.notNull(user, "User can not be null");
        user.validate();
        return userRepository.update(user);
    }

    @Override
    public User deleteById(int id) {
        return userRepository.deleteById(id);
    }

    @WithSpan
    @Override
    public User createWithJms(@SpanAttribute User user) {
        queueMessageSender.send(user);
        return user;
    }

    @Override
    public User createWithKafka(User user) {
        topicMessageSender.send(user);
        return user;
    }
}
