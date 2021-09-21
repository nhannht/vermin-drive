package io.github.nhannht.vermindrive.controller;

import io.github.nhannht.vermindrive.model.User;
import io.github.nhannht.vermindrive.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;

@Controller
@RequestMapping("/signup")
public class SignupController {
    Logger logger = LoggerFactory.getLogger(SignupController.class);

    private final UserService userService;

    public SignupController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping()
    public String signupView() {
        return "signup";
    }

    @PostMapping()
    public String signupUser(@ModelAttribute User user, Model model) throws NoSuchAlgorithmException, InvalidKeySpecException {
        logger.error(String.format("Username is %s",user.getUserName()));
        logger.error(String.format("Password is %s",user.getPassword()));
        String signupError = null;
        if (!userService.isUsernameAvailable(user.getUserName()))
            signupError = "The username '" + user.getUserName() + "' already exists.";


        if (signupError == null) {
            int rowsAdded = userService.createUser(user);

            if (rowsAdded < 0)
                signupError = "There is a error. Cannot sign up. May be this username exist";

        }

        if (signupError != null) {
            model.addAttribute("signupError", signupError);

            return "signup";
        }

        model.addAttribute("signupSuccess", true);
        return "login";
    }


}
