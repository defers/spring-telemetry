package com.defers.springtelemetry.domain.user.port.in;

import com.defers.springtelemetry.domain.user.model.User;
import java.util.List;

public interface UserUseCase {
    List<User> findAll();

    User create(User user);

    User update(int id, User user);

    User deleteById(int id);

    User createWithJms(User user);

    User createWithKafka(User user);
}
