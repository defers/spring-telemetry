package com.defers.springtelemetry.infra.rest;

import com.defers.springtelemetry.domain.user.model.User;
import java.util.List;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.service.annotation.GetExchange;
import org.springframework.web.service.annotation.PostExchange;

public interface UserRestApi {

    @GetExchange("/camel-app/users")
    List<User> getAllUsers();

    @GetExchange("api/users/proxy")
    List<User> getAllUsersCamel();

    @PostExchange("/camel-app/users")
    User create(@RequestBody User user);
}
