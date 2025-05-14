package ru.job4j.todo.mapstruct;

import org.mapstruct.Mapper;
import ru.job4j.todo.dto.user.UserCreateDto;
import ru.job4j.todo.dto.user.UserSessionDto;
import ru.job4j.todo.model.User;

@Mapper(componentModel = "spring")
public interface UserMapper {
    UserSessionDto getDto(User user);

    User getEntityOnCreate(UserCreateDto userDto);
}
