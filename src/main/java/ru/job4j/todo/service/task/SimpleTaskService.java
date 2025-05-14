package ru.job4j.todo.service.task;

import lombok.AllArgsConstructor;
import lombok.extern.jbosslog.JBossLog;
import org.springframework.stereotype.Service;
import ru.job4j.todo.dto.task.TaskCreateDto;
import ru.job4j.todo.dto.task.TaskDto;
import ru.job4j.todo.dto.task.TaskListingDto;
import ru.job4j.todo.dto.user.UserSessionDto;
import ru.job4j.todo.mapstruct.TaskMapper;
import ru.job4j.todo.model.Task;
import ru.job4j.todo.model.User;
import ru.job4j.todo.repository.task.TaskRepository;
import ru.job4j.todo.service.user.UserService;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@JBossLog
@Service
@AllArgsConstructor
public class SimpleTaskService implements TaskService {
    private final TaskRepository repository;
    private final TaskMapper mapper;
    private final UserService userService;

    @Override
    public List<TaskListingDto> listAll() {

        return repository.findAll()
                .stream()
                .map(mapper::getListingDto)
                .toList();
    }

    @Override
    public List<TaskListingDto> listAll(String filter) {
        if (Objects.isNull(filter) || filter.isBlank() || "all".equals(filter)) {
            return listAll();
        }
        return repository.findByCompleted("completed".equals(filter))
                .stream()
                .map(mapper::getListingDto)
                .toList();
    }

    @Override
    public Optional<TaskListingDto> save(TaskCreateDto taskDto, UserSessionDto user) {
        Optional<User> userEntity = userService.findById(user.getId());
        if (userEntity.isEmpty()) {
            return Optional.empty();
        }
        Task entity = mapper.getEntityOnCreate(taskDto, userEntity.get());
        return repository.save(entity)
                .map(mapper::getListingDto);
    }

    @Override
    public Optional<TaskDto> findById(Long id) {
        return repository.findById(id)
                .map(mapper::getDto);
    }

    @Override
    public boolean update(TaskDto taskDto, UserSessionDto user) {
        Optional<User> userEntity = userService.findById(user.getId());
        if (userEntity.isEmpty()) {
            return false;
        }
        Task entity = mapper.getEntityOnUpdate(taskDto, userEntity.get());
        return repository.update(entity);
    }

    @Override
    public boolean setCompleted(Long id, boolean completed) {
        return repository.setCompleted(id, completed);
    }

    @Override
    public boolean delete(Long id) {
        return repository.delete(id);
    }
}
