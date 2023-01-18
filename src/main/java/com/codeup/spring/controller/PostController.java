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
    public String show(Model model) {
        model.addAttribute("posts", postDao.findAll());
        return "posts/show";
    }

    @PostMapping("/posts/show")
    public void viewById(@RequestParam(name = "id") long id, HttpServletResponse resp, HttpServletRequest req) throws IOException {
        resp.sendRedirect("/posts/index/" + id);
    }

    @GetMapping(path = "/posts/index/{id}")
    public String indexById(@PathVariable long id, Model model) {
        Post post = postDao.getReferenceById(id);
        model.addAttribute("post", post);
        return "/posts/index";
    }

    @RequestMapping(path = "/posts/create", method = RequestMethod.GET)
    public String createPostForm() {
        return "/posts/create";
    }

    @PostMapping("/posts/create")
    public void createPost(@RequestParam(name = "title") String title, @RequestParam(name = "body") String body, HttpServletResponse resp, HttpServletRequest req) throws IOException {
        Post post = new Post(title, body);
        postDao.save(post);
        resp.sendRedirect("/posts/show");
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
