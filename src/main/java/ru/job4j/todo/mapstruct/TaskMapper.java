package ru.job4j.todo.mapstruct;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import ru.job4j.todo.dto.task.TaskCreateDto;
import ru.job4j.todo.dto.task.TaskDto;
import ru.job4j.todo.dto.task.TaskListingDto;
import ru.job4j.todo.model.Task;
import ru.job4j.todo.model.User;

@Mapper(componentModel = "spring")
public interface TaskMapper {
    @Mapping(source = "completed", target = "completed", defaultValue = "false")
    @Mapping(source = "user.name", target = "userName", defaultValue = "unknown")
    TaskListingDto getListingDto(Task task);

    @Mapping(source = "completed", target = "completed", defaultValue = "false")
    TaskDto getDto(Task task);

    @Mapping(source = "taskDto.name", target = "name")
    @Mapping(source = "taskDto.description", target = "description")
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "created", ignore = true)
    @Mapping(source = "user", target = "user")
    Task getEntityOnCreate(TaskCreateDto taskDto, User user);

    @Mapping(source = "taskDto.id", target = "id")
    @Mapping(source = "taskDto.name", target = "name")
    @Mapping(source = "taskDto.description", target = "description")
    @Mapping(source = "taskDto.completed", target = "completed", defaultValue = "false")
    @Mapping(target = "created", ignore = true)
    @Mapping(source = "user", target = "user")
    Task getEntityOnUpdate(TaskDto taskDto, User user);
}
