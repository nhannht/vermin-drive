package io.github.nhannht.vermindrive.controller;

import io.github.nhannht.vermindrive.service.FileService;
import io.github.nhannht.vermindrive.service.UserService;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Controller
@RequestMapping("/home")
public class HomeController {

    private final UserService users;
    private final FileService files;

    public HomeController(
            UserService users,
            FileService files
    ) {
        this.users = users;
        this.files = files;
    }

    @GetMapping()
    public String homePage(Authentication authentication, Model model) {

        try {
            var UID = users.getUser(
                            authentication.getName()
                    ).getUserId()
                    .toString();

            model.addAttribute("files", files.allBy(UID));

        } catch (Exception ignored) {
            return "redirect:/logout-success";
        }

        return "home";
    }


}
