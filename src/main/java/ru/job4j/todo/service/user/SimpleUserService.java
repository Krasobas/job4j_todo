package ru.job4j.todo.service.user;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.job4j.todo.dto.UserDto;
import ru.job4j.todo.mapstruct.UserMapper;
import ru.job4j.todo.model.User;
import ru.job4j.todo.repository.user.UserRepository;

import java.util.Optional;

@Service
@AllArgsConstructor
public class SimpleUserService implements UserService {
    private final UserRepository repository;
    private final UserMapper mapper;

    @Override
    public boolean save(UserDto user) {
        User entity = mapper.getEntityFromDto(user);
        return repository.save(entity);
    }

    @Override
    public Optional<UserDto> findByEmailAndPassword(String email, String password) {
        return repository.findByEmailAndPassword(email, password)
                .map(mapper::getModelFromEntity);
    }
}
