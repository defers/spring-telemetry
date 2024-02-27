package com.defers.springtelemetry.domain.user.port.out;

import com.defers.springtelemetry.domain.user.model.User;
import java.util.List;

public interface UserRepository {
    List<User> findAll();

    User create(User user);

    User update(User user);

    User findById(int id);

    User deleteById(int id);
}
