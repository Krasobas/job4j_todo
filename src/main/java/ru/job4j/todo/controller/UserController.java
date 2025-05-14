package ru.job4j.todo.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.job4j.todo.dto.user.UserCreateDto;
import ru.job4j.todo.dto.user.UserSessionDto;
import ru.job4j.todo.dto.user.UserLoginDto;
import ru.job4j.todo.service.user.UserService;

import java.util.Optional;

@Controller
@RequestMapping("/users")
@AllArgsConstructor
public class UserController {
    private final UserService userService;

    @GetMapping("/login")
    public String getLoginPage() {
        return "users/login";
    }

    @PostMapping("/login")
    public String login(@ModelAttribute("user") UserLoginDto user, Model model, HttpServletRequest request) {
        Optional<UserSessionDto> found = userService.findByEmailAndPassword(user.getEmail(), user.getPassword());
        if (found.isEmpty()) {
            model.addAttribute("error", "Account not found, please enter a valid email or password");
            return "users/login";
        }
        var session = request.getSession();
        session.setAttribute("user", found.get());
        return "redirect:/tasks";
    }

    @GetMapping("/register")
    public String getRegistrationPage() {
        return "users/register";
    }

    @PostMapping("/register")
    public String register(@ModelAttribute UserCreateDto user, Model model) {
        if (!userService.save(user)) {
            model.addAttribute("error", "User with this email already exists");
            return "/users/register";
        }
        return "redirect:/users/login";
    }

    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/";
    }

}
