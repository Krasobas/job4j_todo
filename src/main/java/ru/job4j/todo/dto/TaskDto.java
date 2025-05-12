package ru.job4j.todo.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class TaskDto {
    @EqualsAndHashCode.Include
    private Long id;
    private String name;
    private String description;
    private LocalDateTime created;
    private boolean completed = false;

    public boolean getCompleted() {
        return completed;
    }
}
