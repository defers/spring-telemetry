package com.defers.springtelemetry.infra.rest;

import com.defers.springtelemetry.domain.user.model.User;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class ServiceRestConnector {
    private static final Logger log = LoggerFactory.getLogger(ServiceRestConnector.class);
    private final UserRestApi userRestApi;

    public ServiceRestConnector(UserRestApi userRestApi) {
        this.userRestApi = userRestApi;
    }

    public List<User> getAll() {
        return userRestApi.getAllUsers();
    }

    public List<User> getAllCamel() {
        return userRestApi.getAllUsersCamel();
    }

    public User create(User user) {
        log.info("Send api request to create User");
        return userRestApi.create(user);
    }
}
