package ru.job4j.todo.service.category;

import lombok.AllArgsConstructor;
import lombok.extern.jbosslog.JBossLog;
import org.springframework.stereotype.Service;
import ru.job4j.todo.model.Category;
import ru.job4j.todo.repository.category.CategoryRepository;

import java.util.Collection;

@JBossLog
@Service
@AllArgsConstructor
public class SimpleCategoryService implements CategoryService {
    private final CategoryRepository repository;

    @Override
    public Collection<String> listAll() {
        return repository.findAll()
                .stream()
                .map(Category::getName)
                .toList();
    }

    @Override
    public Collection<Category> getByName(Collection<String> names) {
        return repository.findByName(names);
    }
}
