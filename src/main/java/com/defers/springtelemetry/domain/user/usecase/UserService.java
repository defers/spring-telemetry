package com.defers.springtelemetry.domain.user.usecase;

import com.defers.springtelemetry.domain.user.model.User;
import com.defers.springtelemetry.domain.user.port.in.UserUseCase;
import com.defers.springtelemetry.domain.user.port.out.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.Assert;

import java.util.List;

public class UserService implements UserUseCase {
    private static final Logger log = LoggerFactory.getLogger(UserService.class);
    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
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
}
