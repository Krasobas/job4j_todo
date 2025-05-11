package ru.job4j.todo.service.task;

import lombok.AllArgsConstructor;
import lombok.extern.jbosslog.JBossLog;
import org.springframework.stereotype.Service;
import ru.job4j.todo.dto.TaskDto;
import ru.job4j.todo.mapstruct.TaskMapper;
import ru.job4j.todo.model.Task;
import ru.job4j.todo.repository.task.TaskRepository;

import java.util.Collection;
import java.util.Objects;
import java.util.Optional;

@JBossLog
@Service
@AllArgsConstructor
public class SimpleTaskService implements TaskService {
    private final TaskRepository repository;
    private final TaskMapper mapper;

    @Override
    public Collection<TaskDto> listAll() {

        return repository.findAll()
                .stream()
                .map(mapper::getModelFromEntity)
                .toList();
    }

    @Override
    public Collection<TaskDto> listAll(String filter) {
        if (Objects.isNull(filter) || filter.isBlank() || "all".equals(filter)) {
            return listAll();
        }
        return repository.findByCompleted("completed".equals(filter))
                .stream()
                .map(mapper::getModelFromEntity)
                .toList();
    }

    @Override
    public Optional<TaskDto> save(TaskDto taskDto) {
        Task entity = mapper.getEntityFromDto(taskDto);
        return repository.save(entity)
                .map(mapper::getModelFromEntity);
    }

    @Override
    public Optional<TaskDto> findById(Long id) {
        return repository.findById(id)
                .map(mapper::getModelFromEntity);
    }

    @Override
    public boolean update(TaskDto taskDto) {
        log.info(taskDto);
        Task entity = mapper.getEntityFromDto(taskDto);
        log.info(entity);
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
