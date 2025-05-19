package ru.job4j.todo.service.task;

import ru.job4j.todo.dto.task.TaskCreateDto;
import ru.job4j.todo.dto.task.TaskDto;
import ru.job4j.todo.dto.task.TaskListingDto;
import ru.job4j.todo.dto.user.UserSessionDto;

import java.util.List;
import java.util.Optional;

public interface TaskService {

    List<TaskListingDto> listAll(UserSessionDto user);

    List<TaskListingDto> listAll(String filter, UserSessionDto user);

    Optional<TaskListingDto> save(TaskCreateDto taskDto, UserSessionDto user);

    Optional<TaskDto> findById(Long id, UserSessionDto user);

    boolean update(TaskDto taskDto, UserSessionDto user);

    boolean setCompleted(Long id, boolean completed);

    boolean delete(Long id);
}
