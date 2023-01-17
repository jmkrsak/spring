package com.codeup.spring;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Controller
public class PostController {

    @GetMapping("/posts")
    @ResponseBody
    public String posts() {
        return "posts index page";
    }

    @RequestMapping(path = "/posts/{id}", method = RequestMethod.GET)
    @ResponseBody
    public String viewPost(@PathVariable long id) {
        return "view an individual post. This is post number " + id;
    }

    @RequestMapping(path = "/posts/create", method = RequestMethod.GET)
    @ResponseBody
    public String viewCreateForm() {
        return "view the form for creating a post";
    }

    @PostMapping("/posts/create")
    @ResponseBody
    public String createPost() {
        return "create a new post";
    }

    @RequestMapping(path = "/roll-dice", method = RequestMethod.GET)
    public String rollDice() {
        return "roll-dice";
    }

    @PostMapping("/roll-dice")
    public void rolledDice(@RequestParam(name = "guess") int guess, HttpServletResponse resp) throws IOException {
        resp.sendRedirect("/roll-dice/" + guess);
    }

    @GetMapping(path = "/roll-dice/{guess}")
        public String showGuess(@PathVariable int guess, Model model) {

            int random = (int) (Math.random() * 6) + 1;

            String message;

            boolean checkInput = guess == random;
            if (checkInput) {
                message = "You guessed correctly!";
            } else {
                message = "You guessed incorrectly!";
            }

            model.addAttribute("random", random);
            model.addAttribute("guess", guess);
            model.addAttribute("checkInput", message);

            return "roll-dice-n";

        }

}
