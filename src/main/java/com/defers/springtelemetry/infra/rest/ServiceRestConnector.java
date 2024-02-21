package com.defers.springtelemetry.infra.rest;

import com.defers.springtelemetry.domain.user.model.User;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ServiceRestConnector {
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
}
