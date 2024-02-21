package com.defers.springtelemetry.infra.rest;

import com.defers.springtelemetry.domain.user.model.User;
import org.springframework.web.service.annotation.GetExchange;

import java.util.List;

public interface UserRestApi {

    @GetExchange("/camel-app/users")
    List<User> getAllUsers();

    @GetExchange("api/users/proxy")
    List<User> getAllUsersCamel();
}
