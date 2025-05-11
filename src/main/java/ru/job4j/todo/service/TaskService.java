package ru.job4j.todo.service;

import ru.job4j.todo.dto.TaskDto;

import java.util.Collection;
import java.util.Optional;

public interface TaskService {

    Collection<TaskDto> listAll();

    Collection<TaskDto> listAll(String filter);

    Optional<TaskDto> save(TaskDto TaskDto);

    Optional<TaskDto> findById(Long id);

    boolean update(TaskDto TaskDto);

    boolean setCompleted(Long id, boolean completed);

    boolean delete(Long id);
}
