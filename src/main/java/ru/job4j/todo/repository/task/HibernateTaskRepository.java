package ru.job4j.todo.repository.task;

import lombok.AllArgsConstructor;
import lombok.extern.jbosslog.JBossLog;
import org.springframework.stereotype.Repository;
import ru.job4j.todo.model.Task;
import ru.job4j.todo.repository.CrudRepository;

import java.util.Collection;
import java.util.Collections;
import java.util.Map;
import java.util.Optional;

@JBossLog
@Repository
@AllArgsConstructor
public class HibernateTaskRepository implements TaskRepository {
    private final CrudRepository crudRepository;

    @Override
    public Optional<Task> save(Task task) {
        try {
            crudRepository.run(session -> session.persist(task));
            return Optional.of(task);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
        return Optional.empty();
    }

    @Override
    public Optional<Task> findById(Long id) {
        try {
            return crudRepository.optional(
                    "SELECT DISTINCT t FROM Task t JOIN FETCH t.priority LEFT JOIN FETCH t.categories WHERE t.id = :fId",
                    Task.class,
                    Map.of("fId", id)
            );
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
        return Optional.empty();
    }

    @Override
    public Collection<Task> findAll() {
        try {
            return crudRepository.query(
                    "SELECT DISTINCT t FROM Task t JOIN FETCH t.priority LEFT JOIN FETCH t.categories ORDER BY t.completed ASC, t.priority.position ASC, t.created ASC, t.name ASC",
                    Task.class);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
        return Collections.emptyList();
    }

    @Override
    public Collection<Task> findByCompleted(boolean completed) {
        try {
            return crudRepository.query(
                    "SELECT DISTINCT t FROM Task t JOIN FETCH t.priority LEFT JOIN FETCH t.categories WHERE t.completed = :fCompleted ORDER BY t.priority.position ASC, t.created ASC, t.name ASC",
                    Task.class,
                    Map.of("fCompleted", completed)
            );
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
        return Collections.emptyList();
    }

    @Override
    public boolean update(Task task) {
        try {
            return findById(task.getId())
                    .map(origin -> {
                        origin.update(task);
                        crudRepository.run(session -> session.update(origin));
                        return origin;
                    }).isPresent();
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
        return false;
    }

    @Override
    public boolean setCompleted(Long id, boolean completed) {
        try {
            return crudRepository.run(
                    "UPDATE Task SET completed = :fCompleted WHERE id = :fId",
                    Map.of(
                            "fId", id,
                            "fCompleted", completed
                    )
            ) > 0;
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
        return false;
    }

    @Override
    public boolean delete(Long id) {
        try {
            return crudRepository.run(
                    "DELETE FROM Task WHERE id = :fId",
                    Map.of("fId", id)
            ) > 0;
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
        return false;
    }
}
