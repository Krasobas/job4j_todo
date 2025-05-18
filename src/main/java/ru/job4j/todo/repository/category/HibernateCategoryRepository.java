package ru.job4j.todo.repository.category;

import lombok.AllArgsConstructor;
import lombok.extern.jbosslog.JBossLog;
import org.springframework.stereotype.Repository;
import ru.job4j.todo.model.Category;
import ru.job4j.todo.repository.CrudRepository;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@JBossLog
@Repository
@AllArgsConstructor
public class HibernateCategoryRepository implements CategoryRepository {

    private final CrudRepository crudRepository;

    @Override
    public Collection<Category> findAll() {
        try {
            return crudRepository.query(
                    "FROM Category",
                    Category.class
            );
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
        return List.of();
    }

    @Override
    public Optional<Category> findByName(String name) {
        try {
            return crudRepository.optional(
                    "FROM Category WHERE name = :fName",
                    Category.class,
                    Map.of("fName", name)
            );
        } catch (Exception e) {

            return Optional.empty();
        }
    }

    @Override
    public Collection<Category> findByName(Collection<String> names) {
        try {
            return crudRepository.query(
                    "FROM Category WHERE name IN :fNames",
                    Category.class,
                    Map.of("fNames", names)
            );
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
        return List.of();
    }
}
