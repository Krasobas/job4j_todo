package ru.job4j.todo.repository;

import ru.job4j.todo.model.Task;

import java.util.Collection;
import java.util.Optional;

public interface TaskRepository {

    Optional<Task> save(Task task);

    Optional<Task> findById(Long id);

    Collection<Task> findAll();

    Collection<Task> findByCompleted(boolean completed);

    boolean update(Task task);

    boolean setCompleted(Long id, boolean completed);

    boolean delete(Long id);
}
