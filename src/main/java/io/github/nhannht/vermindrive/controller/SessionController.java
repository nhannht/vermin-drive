package io.github.nhannht.vermindrive.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class SessionController {

    @GetMapping("/logout-success")
    public String quit(Model model) {
        model.addAttribute("logoutSuccess", true);

        return "login";
    }


}
