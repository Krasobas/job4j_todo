package ru.job4j.todo.mapstruct;

import org.mapstruct.Mapper;
import ru.job4j.todo.dto.UserDto;
import ru.job4j.todo.model.User;

@Mapper(componentModel = "spring")
public interface UserMapper {
    UserDto getModelFromEntity(User user);

    User getEntityFromDto(UserDto userDto);
}
