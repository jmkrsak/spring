package com.codeup.spring.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class AuthenticationController {

    @GetMapping("/login")
    public String showLoginForm() {
        return "login";
    }

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

}
