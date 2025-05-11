package ru.job4j.todo.controller.error;

import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/error")
public class CustomErrorController implements ErrorController {

    @GetMapping
    public String handleGenericError() {
        return "errors/error";
    }

    @GetMapping("/400")
    public String handleBadRequest() {
        return "errors/error-400";
    }

    @GetMapping("/403")
    public String handleForbidden() {
        return "errors/error-403";
    }

    @GetMapping("/404")
    public String handleNotFound() {
        return "errors/error-404";
    }

    @GetMapping("/500")
    public String handleServerError() {
        return "errors/error-500";
    }

    // Required for Spring Boot compatibility (optional in newer versions)
    public String getErrorPath() {
        return "/error";
    }
}
