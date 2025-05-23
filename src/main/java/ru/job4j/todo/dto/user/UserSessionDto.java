package ru.job4j.todo.dto.user;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class UserSessionDto {
    @EqualsAndHashCode.Include
    private Long id;
    private String name;
    private String email;
    private String timeZone;
}
