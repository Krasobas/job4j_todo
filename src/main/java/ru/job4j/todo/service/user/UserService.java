package ru.job4j.todo.service.user;

import ru.job4j.todo.dto.user.UserCreateDto;
import ru.job4j.todo.dto.user.UserSessionDto;
import ru.job4j.todo.model.User;

import java.util.*;
import java.util.stream.Collectors;

public interface UserService {
    Map<String, String> TIME_ZONES = Arrays.stream(TimeZone.getAvailableIDs())
            .collect(Collectors.toMap(
                    zoneId -> {
                        TimeZone tz = TimeZone.getTimeZone(zoneId);
                        return tz.getDisplayName();
                    },
                    zoneId -> zoneId,
                    (oldValue, newValue) -> oldValue,
                    TreeMap::new
            ));

    boolean save(UserCreateDto user);

    Optional<UserSessionDto> findByEmailAndPassword(String email, String password);

    Optional<User> findById(Long id);

    default Map<String, String> listTimeZones() {
        return TIME_ZONES;
    }
}
