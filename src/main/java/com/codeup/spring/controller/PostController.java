package com.codeup.spring.controller;

import com.codeup.spring.models.Post;
import com.codeup.spring.repository.PostRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Controller
public class PostController {

    private final PostRepository postDao;

    public PostController(PostRepository postDao) {
        this.postDao = postDao;
    }

    @GetMapping("/posts/show")
    public String index(Model model) {
        model.addAttribute("posts", postDao.findAll());
        return "posts/show";
    }

    @GetMapping("/posts/index")
    @ResponseBody
    public String posts() {
        return "posts index page";
    }

    @RequestMapping(path = "/posts/index/{id}", method = RequestMethod.GET)
    @ResponseBody
    public String viewPost(@PathVariable long id) {
        return "view an individual post. This is post number " + id;
    }

    @RequestMapping(path = "/posts/create", method = RequestMethod.GET)
    public String createPostForm() {
        return "/posts/create";
    }

    @PostMapping("/posts/create")
    public void createPost(@RequestParam(name = "title") String title, @RequestParam(name = "body") String body, HttpServletResponse response, HttpServletRequest request) throws IOException {
        Post post = new Post(title, body);
        postDao.save(post);
        response.sendRedirect("/posts/show");
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
