package ru.job4j.todo.controller;

import lombok.AllArgsConstructor;
import lombok.extern.jbosslog.JBossLog;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.job4j.todo.dto.TaskDto;
import ru.job4j.todo.service.task.TaskService;


import java.util.Optional;

@JBossLog
@Controller
@RequestMapping("/tasks")
@AllArgsConstructor
public class TaskController {

    private final TaskService service;

    @GetMapping
    public String getAll(@RequestParam(name = "filter", required = false) String filter, Model model) {
        model.addAttribute("tasks", service.listAll(filter))
            .addAttribute("filter", filter);
        return "tasks/task-list";
    }

    @GetMapping("/new")
    public String getCreateForm(Model model) {
        model.addAttribute("task", new TaskDto());
        return "tasks/task-form";
    }

    @GetMapping("/{id}/edit")
    public String getEditForm(@PathVariable("id") Long id, Model model) {
        Optional<TaskDto> task = service.findById(id);
        if (task.isEmpty()) {
            model.addAttribute("error", "Task not found.");
            return "redirect:/error/404";
        }
        model.addAttribute("task", task.get());
        return "tasks/task-edit";
    }

    @PostMapping
    public String create(@ModelAttribute TaskDto task, Model model) {
        Optional<TaskDto> created = service.save(task);
        log.infof("created: %s", created);
        if (created.isEmpty()) {
            model.addAttribute("error", "Something went wrong. Please try again.");
            return "redirect:/error/500";
        }
        return "redirect:/tasks?filter=new";
    }

    @GetMapping("/{id}")
    public String getById(@PathVariable("id") Long id, Model model) {
        Optional<TaskDto> task = service.findById(id);
        if (task.isEmpty()) {
            model.addAttribute("error", "Task not found.");
            return "redirect:/error/404";
        }
        model.addAttribute("task", task.get());
        return "tasks/task-detail";
    }

    @PatchMapping("/{id}/complete")
    public String markComplete(@PathVariable Long id, Model model) {
        boolean updated = service.setCompleted(id, true);
        if (!updated) {
            model.addAttribute("error", "Task not found.");
            return "redirect:/error/404";
        }
        return String.format("redirect:/tasks/%s", id);
    }

    @PatchMapping("/{id}/incomplete")
    public String markIncomplete(@PathVariable Long id, Model model) {
        boolean updated = service.setCompleted(id, false);
        if (!updated) {
            model.addAttribute("error", "Task not found.");
            return "redirect:/error/404";
        }
        return String.format("redirect:/tasks/%s", id);
    }

    @PutMapping("/{id}/update")
    public String update(@PathVariable Long id, @ModelAttribute TaskDto task, Model model) {
        log.warnf("before update: %s", task);
        boolean updated = service.update(task);
        if (!updated) {
            model.addAttribute("error", "Task not found.");
            return "redirect:/error/404";
        }
        return String.format("redirect:/tasks/%s", id);
    }

    @DeleteMapping("/{id}/delete")
    public String delete(@PathVariable Long id, Model model) {
        boolean deleted = service.delete(id);
        if (!deleted) {
            model.addAttribute("error", "Task not found.");
            return "redirect:/error/404";
        }
        return "redirect:/tasks";
    }
}
