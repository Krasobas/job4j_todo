package ru.job4j.todo.service;

import lombok.AllArgsConstructor;
import lombok.extern.jbosslog.JBossLog;
import org.springframework.stereotype.Service;
import ru.job4j.todo.model.Task;
import ru.job4j.todo.repository.TaskRepository;

import java.util.Collection;
import java.util.Objects;
import java.util.Optional;

@JBossLog
@Service
@AllArgsConstructor
public class SimpleTaskService implements TaskService {
    private final TaskRepository repository;

    @Override
    public Collection<Task> listAll() {
        return repository.findAll();
    }

    @Override
    public Collection<Task> listAll(String filter) {
        if (Objects.isNull(filter) || filter.isBlank() || "all".equals(filter)) {
            return listAll();
        }
        return repository.findByCompleted("completed".equals(filter));
    }

    @Override
    public Optional<Task> save(Task task) {
        return repository.save(task);
    }

    @Override
    public Optional<Task> findById(Long id) {
        return repository.findById(id);
    }

    @Override
    public boolean update(Task task) {
        return repository.update(task);
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
