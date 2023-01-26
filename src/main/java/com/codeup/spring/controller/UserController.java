package com.codeup.spring.controller;

import com.codeup.spring.models.User;
import com.codeup.spring.services.UserService;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@NoArgsConstructor
public class UserController {

    @Autowired
    private UserService userServ;

    public UserController(UserService userServ) {

        this.userServ = userServ;

    }

    @GetMapping("/sign-up")
    public String showSignupForm(Model model) {

        model.addAttribute("user", new User());

        return "/users/sign-up";

    }

    @PostMapping("/sign-up")
    public String saveUser(@ModelAttribute User user) {

        userServ.saveUser(user);

        return "redirect:/login";

    }

}
