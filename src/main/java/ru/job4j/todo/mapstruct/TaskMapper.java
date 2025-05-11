package ru.job4j.todo.mapstruct;

import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import ru.job4j.todo.dto.TaskDto;
import ru.job4j.todo.model.Task;

@Mapper(componentModel = "spring")
public interface TaskMapper {
    @Mapping(source = "completed", target = "completed", defaultValue = "false")
    TaskDto getModelFromEntity(Task task);

    @InheritInverseConfiguration
    Task getEntityFromDto(TaskDto taskDto);
}
