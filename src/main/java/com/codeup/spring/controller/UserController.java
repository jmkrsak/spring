package com.codeup.spring.controller;

import com.codeup.spring.models.User;
import com.codeup.spring.repositories.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;

@Controller
public class UserController {

    private UserRepository usersDao;
    private PasswordEncoder passwordEncoder;

    public UserController(UserRepository usersDao, PasswordEncoder passwordEncoder) {
        this.usersDao = usersDao;
        this.passwordEncoder = passwordEncoder;
    }

//    @GetMapping("/users/login")
//    public String showSignUpForm(Model model) {
//        model.addAttribute("user", new User());
//        return "/users/login";
//    }
//
//    @PostMapping("/users/login")
//    public String loginUser(@ModelAttribute User user) {
//        User userFromDb = usersDao.findByUsername(user.getUsername());
//        if (userFromDb == null) {
//            return "redirect:/users/login";
//        } else if (passwordEncoder.matches(user.getPassword(), userFromDb.getPassword())) {
//            return "redirect:/posts/create";
//        } else {
//            return "redirect:/users/login";
//        }
//    }

    @GetMapping("/users/sign-up")
    public String showSignupForm(Model model) {
        model.addAttribute("user", new User());
        return "/users/sign-up";

    }

    @PostMapping("/users/sign-up")
    public String saveUser(@ModelAttribute User user) {
        String hash = passwordEncoder.encode(user.getPassword());
        user.setPassword(hash);
        usersDao.save(user);
        return "redirect:/users/login";
    }

}
