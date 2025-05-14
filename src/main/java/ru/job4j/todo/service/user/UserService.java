package ru.job4j.todo.service.user;

import ru.job4j.todo.dto.user.UserCreateDto;
import ru.job4j.todo.dto.user.UserSessionDto;
import ru.job4j.todo.model.User;

import java.util.Optional;

public interface UserService {
    boolean save(UserCreateDto user);

    Optional<UserSessionDto> findByEmailAndPassword(String email, String password);

    Optional<User> findById(Long id);
}
