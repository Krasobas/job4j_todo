package ru.job4j.todo.repository.category;

import ru.job4j.todo.model.Category;

import java.util.Collection;
import java.util.Optional;

public interface CategoryRepository {

    Collection<Category> findAll();

    Optional<Category> findByName(String name);

    Collection<Category> findByName(Collection<String> names);
}
