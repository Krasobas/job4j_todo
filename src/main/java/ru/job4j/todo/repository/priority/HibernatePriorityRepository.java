package ru.job4j.todo.repository.priority;

import lombok.AllArgsConstructor;
import lombok.extern.jbosslog.JBossLog;
import org.springframework.stereotype.Repository;
import ru.job4j.todo.model.Priority;
import ru.job4j.todo.repository.CrudRepository;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@JBossLog
@Repository
@AllArgsConstructor
public class HibernatePriorityRepository implements PriorityRepository {
    private final CrudRepository crudRepository;

    @Override
    public Collection<Priority> findAll() {
        try {
            return crudRepository.query(
                    "FROM Priority p ORDER BY p.position ASC",
                    Priority.class
            );

        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
        return List.of();
    }

    @Override
    public Optional<Priority> findByName(String name) {
        try {
            return crudRepository.optional(
                    "FROM Priority p WHERE p.name = :fName",
                    Priority.class,
                    Map.of("fName", name)
            );
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
        return Optional.empty();
    }

    @Override
    public Optional<Priority> findById(Long id) {
        try {
            return crudRepository.optional(
                    "FROM Priority p WHERE p.id = :fId",
                    Priority.class,
                    Map.of("fId", id)
            );
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
        return Optional.empty();
    }
}
