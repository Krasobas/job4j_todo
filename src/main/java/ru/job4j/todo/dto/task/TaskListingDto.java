package ru.job4j.todo.dto.task;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class TaskListingDto {
    @EqualsAndHashCode.Include
    private Long id;
    private String name;
    private LocalDateTime created;
    private boolean completed = false;
    private String userName;
    private String priority;

    public boolean getCompleted() {
        return completed;
    }
}
