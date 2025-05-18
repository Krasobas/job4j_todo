package ru.job4j.todo.dto.task;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class TaskCreateDto {
    @EqualsAndHashCode.Include
    private String name;
    private String description;
    private String priority;
    private List<String> categories = new ArrayList<>();
}
