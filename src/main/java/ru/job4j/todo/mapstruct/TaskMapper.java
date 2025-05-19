package ru.job4j.todo.mapstruct;

import org.mapstruct.Context;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import ru.job4j.todo.dto.task.TaskCreateDto;
import ru.job4j.todo.dto.task.TaskDto;
import ru.job4j.todo.dto.task.TaskListingDto;
import ru.job4j.todo.dto.user.UserSessionDto;
import ru.job4j.todo.model.Category;
import ru.job4j.todo.model.Priority;
import ru.job4j.todo.model.Task;
import ru.job4j.todo.model.User;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.*;

@Mapper(componentModel = "spring")
public interface TaskMapper {

    @Mapping(source = "task.id", target = "id")
    @Mapping(source = "task.name", target = "name")
    @Mapping(source = "task.completed", target = "completed", defaultValue = "false")
    @Mapping(source = "task.user.name", target = "userName", defaultValue = "unknown")
    @Mapping(source = "task.priority.name", target = "priority", defaultValue = "normal")
    @Mapping(source = "task.categories", target = "categories")
    @Mapping(source = "task.created", target = "created")
    TaskListingDto getListingDto(Task task, @Context UserSessionDto user);

    @Mapping(source = "task.id", target = "id")
    @Mapping(source = "task.name", target = "name")
    @Mapping(source = "task.completed", target = "completed", defaultValue = "false")
    @Mapping(source = "task.priority.name", target = "priority", defaultValue = "normal")
    @Mapping(source = "task.categories", target = "categories")
    @Mapping(source = "task.created", target = "created")
    TaskDto getDto(Task task, @Context UserSessionDto user);

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

    default ZonedDateTime mapCreated(LocalDateTime created, @Context UserSessionDto user) {
        if (Objects.isNull(created)) {
            created = LocalDateTime.now();
        }
        String userZone = user.getTimeZone();
        ZoneId zoneId = Objects.isNull(userZone) || userZone.isBlank() ? TimeZone.getDefault().toZoneId() : ZoneId.of(userZone);
        return created.atZone(ZoneId.systemDefault()).withZoneSameInstant(zoneId);
    }
}
