package com.codeup.spring.controller;

import com.codeup.spring.models.Post;
import com.codeup.spring.models.User;
import com.codeup.spring.repositories.PostRepository;
import com.codeup.spring.repositories.UserRepository;
import com.codeup.spring.services.EmailService;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static org.springframework.data.jpa.domain.AbstractPersistable_.id;

@Controller
@NoArgsConstructor
public class PostController {

    @Autowired
    private PostRepository postDao;

    @Autowired
    private UserRepository userDao;

    @Autowired
    private EmailService emailService;

    public PostController(PostRepository postDao, UserRepository userDao, EmailService emailService) {
        this.postDao = postDao;
        this.userDao = userDao;
        this.emailService = emailService;
    }

    @GetMapping("/posts/show")
    public String show(Model model) {
        model.addAttribute("posts", postDao.findAll());
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        System.out.println(user.getUsername());
        return "/posts/show";
    }

//    @PostMapping("/posts/show")
//    public void viewById(@RequestParam(name = "id") long id, HttpServletResponse resp, HttpServletRequest req) throws IOException {
//        resp.sendRedirect("/posts/show/" + id);
//
//    }

    @GetMapping(path = "/posts/index/{id}")
    public String indexById(@PathVariable long id, Model model) {
        Post post = postDao.getReferenceById(id);
        model.addAttribute("post", post);
        return "/posts/index";
    }

    @RequestMapping(path = "/posts/create", method = RequestMethod.GET)
    public String createPostForm(Model model) {
        model.addAttribute("post", new Post());
        return "/posts/create";
    }

    @PostMapping("/posts/creates")
    public String createPost(@ModelAttribute Post post) {
        System.out.println("controller reached");
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        System.out.println(user.getUsername());
//        User user = userDao.findByUsername((String) auth.getPrincipal());
        post.setUser(user);
        emailService.prepareAndSend(post, post.getTitle(), post.getBody());
        postDao.save(post);
//        resp.sendRedirect("/posts/show");
        return "redirect:/posts/show";
    }


//    @PostMapping("/create")
//    public String creates(){
//        return "redirect:/posts/show";
//    }

    @RequestMapping(path = "/roll-dice", method = RequestMethod.GET)
    public String rollDice() {
        return "roll-dice";
    }

//    @PostMapping("/roll-dice")
//    public void rolledDice(@RequestParam(name = "guess") int guess, HttpServletResponse resp) throws IOException {
//        resp.sendRedirect("/roll-dice/" + guess);
//    }

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
