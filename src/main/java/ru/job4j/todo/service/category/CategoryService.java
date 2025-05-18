package ru.job4j.todo.service.category;


import ru.job4j.todo.model.Category;

import java.util.Collection;

public interface CategoryService {

    Collection<String> listAll();

    Collection<Category> getByName(Collection<String> names);
}
