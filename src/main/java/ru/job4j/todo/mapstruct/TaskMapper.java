package ru.job4j.todo.mapstruct;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import ru.job4j.todo.dto.task.TaskCreateDto;
import ru.job4j.todo.dto.task.TaskDto;
import ru.job4j.todo.dto.task.TaskListingDto;
import ru.job4j.todo.model.Category;
import ru.job4j.todo.model.Priority;
import ru.job4j.todo.model.Task;
import ru.job4j.todo.model.User;

import java.util.Collection;
import java.util.Collections;
import java.util.List;

@Mapper(componentModel = "spring")
public interface TaskMapper {

    @Mapping(source = "completed", target = "completed", defaultValue = "false")
    @Mapping(source = "user.name", target = "userName", defaultValue = "unknown")
    @Mapping(source = "priority.name", target = "priority", defaultValue = "normal")
    @Mapping(source = "categories", target = "categories")
    TaskListingDto getListingDto(Task task);

    @Mapping(source = "completed", target = "completed", defaultValue = "false")
    @Mapping(source = "priority.name", target = "priority", defaultValue = "normal")
    @Mapping(source = "categories", target = "categories")
    TaskDto getDto(Task task);

    @Mapping(source = "taskDto.name", target = "name")
    @Mapping(source = "taskDto.description", target = "description")
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "created", ignore = true)
    @Mapping(source = "user", target = "user")
    @Mapping(source = "priority", target = "priority")
    @Mapping(source = "categories", target = "categories")
    Task getEntityOnCreate(TaskCreateDto taskDto, User user, Priority priority, Collection<Category> categories);

    @Mapping(source = "taskDto.id", target = "id")
    @Mapping(source = "taskDto.name", target = "name")
    @Mapping(source = "taskDto.description", target = "description")
    @Mapping(source = "taskDto.completed", target = "completed", defaultValue = "false")
    @Mapping(target = "created", ignore = true)
    @Mapping(source = "user", target = "user")
    @Mapping(source = "priority", target = "priority")
    @Mapping(source = "categories", target = "categories")
    Task getEntityOnUpdate(TaskDto taskDto, User user, Priority priority, Collection<Category> categories);

    default List<String> mapCategoryList(Collection<Category> categories) {
        if (categories == null) {
            return Collections.emptyList();
        }
        return categories.stream()
                .map(Category::getName)
                .toList();
    }
}
