package com.defers.springtelemetry.infra.db.repository;

import com.defers.springtelemetry.domain.user.model.User;
import com.defers.springtelemetry.domain.user.port.out.UserRepository;
import com.defers.springtelemetry.infra.db.exception.ElementNotFoundException;
import com.defers.springtelemetry.infra.db.model.UserModel;
import io.micrometer.observation.annotation.Observed;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import org.springframework.core.convert.ConversionService;

public class UserInMemoryRepository implements UserRepository {
    private final Map<Integer, UserModel> users = new HashMap<>();
    private final ConversionService conversionService;

    public UserInMemoryRepository(ConversionService conversionService) {
        this.conversionService = conversionService;

        UserModel admin = new UserModel(1, "Admin");
        UserModel user = new UserModel(2, "User");
        users.put(admin.getId(), admin);
        users.put(user.getId(), user);
    }

    @Observed(
            name = "find.all.users.repo",
            contextualName = "find-all-users-repo",
            lowCardinalityKeyValues = {"users1", "users2"})
    @Override
    public List<User> findAll() {
        return users.values().stream()
                .map(userModel -> conversionService.convert(userModel, User.class))
                .toList();
    }

    @Override
    public User create(User user) {
        users.put(user.getId(), conversionService.convert(user, UserModel.class));
        return user;
    }

    @Override
    public User update(User user) {
        var id = user.getId();
        findById(id);
        var userModel = users.get(id);
        userModel.setName(user.getName());
        return user;
    }

    @Override
    public User findById(int id) {
        return Optional.ofNullable(users.get(id))
                .map(u -> conversionService.convert(u, User.class))
                .orElseThrow(() -> new ElementNotFoundException("User with id %s not found".formatted(id)));
    }

    @Override
    public User deleteById(int id) {
        findById(id);
        return conversionService.convert(users.remove(id), User.class);
    }
}
