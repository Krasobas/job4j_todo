package ru.job4j.todo.service;

import ru.job4j.todo.model.Task;

import java.util.Collection;
import java.util.Optional;

public interface TaskService {

    Collection<Task> listAll();

    Collection<Task> listAll(String filter);

    Optional<Task> save(Task task);

    Optional<Task> findById(Long id);

    boolean update(Task task);

    boolean setCompleted(Long id, boolean completed);

    boolean delete(Long id);
}
