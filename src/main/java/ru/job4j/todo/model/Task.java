package ru.job4j.todo.model;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
@Entity
@Table(name = "tasks")
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Task {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    private Long id;
    private String name;
    private String description;
    @CreationTimestamp
    private LocalDateTime created;
    private boolean completed = false;
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    public boolean getCompleted() {
        return completed;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "priority_id")
    private Priority priority;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "tasks_categories",
            joinColumns = { @JoinColumn(name = "task_id") },
            inverseJoinColumns = { @JoinColumn(name = "category_id") }
    )
    List<Category> categories = new ArrayList<>();

    public void update(Task update) {
        name = update.getName();
        description = update.getDescription();
        completed = update.getCompleted();
        priority = update.getPriority();
        categories = update.getCategories();
    }
}
