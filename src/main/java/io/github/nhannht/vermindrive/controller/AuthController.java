package io.github.nhannht.vermindrive.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
@Controller
public class AuthController {

    @GetMapping("/login")
    public String loginView() {
        return "login";
    }

    @GetMapping("/")
    public String redirectToLogin() {
        return "redirect:/login";
    }

    @GetMapping("/login-error")
    public String loginErrorView(Model model) {
        model.addAttribute("authenticationError", true);

        return "login";
    }


}
