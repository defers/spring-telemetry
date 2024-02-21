package com.defers.springtelemetry.app.rest;

import com.defers.springtelemetry.domain.user.model.User;
import com.defers.springtelemetry.domain.user.port.in.UserUseCase;
import com.defers.springtelemetry.infra.rest.ServiceRestConnector;
import io.micrometer.observation.annotation.Observed;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/users")
@RestController
public class UsersController {
    private static final Logger log = LoggerFactory.getLogger(UsersController.class.getName());
    private final UserUseCase userUseCase;
    private final ServiceRestConnector serviceRestConnector;

    public UsersController(UserUseCase userUseCase, ServiceRestConnector serviceRestConnector) {
        this.userUseCase = userUseCase;
        this.serviceRestConnector = serviceRestConnector;
    }

    @GetMapping
    public List<User> getUsers() {
        log.info("Request to getUsers");
        return userUseCase.findAll();
    }

    @GetMapping("/proxy")
    public List<User> getUsersProxy() {
        log.info("Request to getUsersProxy");
        return serviceRestConnector.getAll();
    }

    @Observed(name = "find.users.proxy.service",
            contextualName = "find-users-proxy.service",
            lowCardinalityKeyValues = {"users1", "users2"})
    @GetMapping("/proxy-camel")
    public List<User> getUsersProxyCamel() {
        log.info("Request to getUsersProxy Camel");
        return serviceRestConnector.getAllCamel();
    }

    @PostMapping
    public User create(@RequestBody User user) {
        log.info("Request to create, with body: %s".formatted(user));
        return userUseCase.create(user);
    }

}
