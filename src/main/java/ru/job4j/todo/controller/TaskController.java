package ru.job4j.todo.controller;

import lombok.AllArgsConstructor;
import lombok.extern.jbosslog.JBossLog;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.job4j.todo.dto.task.TaskCreateDto;
import ru.job4j.todo.dto.task.TaskDto;
import ru.job4j.todo.dto.task.TaskListingDto;
import ru.job4j.todo.dto.user.UserSessionDto;
import ru.job4j.todo.service.priority.PriorityService;
import ru.job4j.todo.service.task.TaskService;


import java.util.Optional;

@JBossLog
@Controller
@RequestMapping("/tasks")
@AllArgsConstructor
public class TaskController {

    private final TaskService service;
    private final PriorityService priorityService;

    @GetMapping
    public String getAll(@RequestParam(name = "filter", required = false) String filter, Model model) {
        model.addAttribute("tasks", service.listAll(filter))
            .addAttribute("filter", filter);
        return "tasks/task-list";
    }

    @GetMapping("/new")
    public String getCreateForm(Model model) {
        model.addAttribute("task", new TaskCreateDto());
        model.addAttribute("priorities", priorityService.listAll());
        return "tasks/task-form";
    }

    @PostMapping
    public String create(@ModelAttribute TaskCreateDto task, @SessionAttribute UserSessionDto user, Model model) {
        Optional<TaskListingDto> created = service.save(task, user);
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

    @GetMapping("/{id}/edit")
    public String getEditForm(@PathVariable("id") Long id, Model model) {
        Optional<TaskDto> task = service.findById(id);
        if (task.isEmpty()) {
            model.addAttribute("error", "Task not found.");
            return "redirect:/error/404";
        }
        model.addAttribute("task", task.get());
        model.addAttribute("priorities", priorityService.listAll());
        return "tasks/task-edit";
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
    public String update(@PathVariable Long id, @ModelAttribute TaskDto task, @SessionAttribute UserSessionDto user, Model model) {
        log.warnf("before update: %s", task);
        boolean updated = service.update(task, user);
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
