package ru.job4j.todo.service.priority;

import lombok.AllArgsConstructor;
import lombok.extern.jbosslog.JBossLog;
import org.springframework.stereotype.Service;
import ru.job4j.todo.model.Priority;
import ru.job4j.todo.repository.priority.PriorityRepository;

import java.util.Collection;
import java.util.Optional;

@JBossLog
@Service
@AllArgsConstructor
public class SimplePriorityService implements PriorityService {
    private final PriorityRepository repository;

    @Override
    public Collection<String> listAll() {
        return repository.findAll()
                .stream()
                .map(Priority::getName)
                .toList();
    }

    @Override
    public Optional<Priority> getByName(String name) {
        return repository.findByName(name);
    }

    @Override
    public Optional<Priority> getById(Long id) {
        return repository.findById(id);
    }
}
