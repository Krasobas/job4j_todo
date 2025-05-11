package ru.job4j.todo.service.user;

import ru.job4j.todo.dto.UserDto;

import java.util.Optional;

public interface UserService {
    boolean save(UserDto user);

    Optional<UserDto> findByEmailAndPassword(String email, String password);
}
