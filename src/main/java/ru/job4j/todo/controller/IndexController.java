package ru.job4j.todo.controller;

import lombok.AllArgsConstructor;
import lombok.extern.jbosslog.JBossLog;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import ru.job4j.todo.service.task.TaskService;

@JBossLog
@Controller
@AllArgsConstructor
public class IndexController {

    private final TaskService service;

    @GetMapping({"/", "/index"})
    public String getIndex() {
        return "index";
    }
}
