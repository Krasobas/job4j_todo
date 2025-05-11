package ru.job4j.todo.repository.user;

import ru.job4j.todo.model.User;

import java.util.Optional;

public interface UserRepository {

    boolean save(User user);

    Optional<User> findByEmailAndPassword(String email, String password);

    boolean deleteById(int id);
}
