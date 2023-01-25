package com.codeup.spring.controller;

import com.codeup.spring.models.Post;
import com.codeup.spring.models.User;
import com.codeup.spring.repositories.PostRepository;
import com.codeup.spring.repositories.UserRepository;
import com.codeup.spring.services.EmailService;
//import com.codeup.spring.services.PostService;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@Controller
@NoArgsConstructor
public class PostController {

    @Autowired
    private PostRepository postDao;

    @Autowired
    private UserRepository userDao;

    @Autowired
    private EmailService emailService;

//    private PostService postService;

    public PostController(PostRepository postDao, UserRepository userDao, EmailService emailService) {
        this.postDao = postDao;
        this.userDao = userDao;
        this.emailService = emailService;

    }

    @GetMapping("/posts")
    public String show(Model model) {
        model.addAttribute("posts", postDao.findAll());
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return "/posts/index";
    }

    @GetMapping("/edit/{id}")
    public String editPostById(@PathVariable long id, Model model) {
//        System.out.println(id);
        Post post = postDao.getReferenceById(id);
        model.addAttribute("post", post);
        return "posts/edit";
    }

    @PostMapping("/delete")
    public String deletePostById(@RequestParam (name="button") long id) {
        postDao.deleteById(id);
        return "redirect:/profile";
    }

    @GetMapping("/show/{id}")
    public String showById(@PathVariable long id, Model model) {

//        User user = postDao.getReferenceById(user);
        Post post = postDao.getReferenceById(id);
        System.out.println(post.getTitle());
        model.addAttribute("post", post);
//        String email = post.getUser().getEmail();
//        model.addAttribute("user", user);
        return "posts/show";
    }

    @PostMapping ("/edit")
    public String editPost(@ModelAttribute Post post) {
        System.out.println(post.getId());
//        Post updatePost = postDao.getReferenceById(id);
//        updatePost.setTitle(post.getTitle());
//        updatePost.setBody(post.getBody());
        postDao.save(post);
        return "redirect:/profile";
    }

//    @PatchMapping("/edit")
//    public String editPost(@ModelAttribute Post post) {
//        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
//        post.setUser(user);
//        postDao.save(post);
////        resp.sendRedirect("/posts/show");
//        return "redirect:/profile";
//    }

    @PostMapping("/posts")
    public String create(@ModelAttribute Post post, @RequestParam(name="button") long id) {
//        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
//        post.setUser(user);
        return "redirect:/show/" + id;
    }

    @PostMapping("/profile")
    public String editThis(@ModelAttribute Post post, @RequestParam(name="button") long id) {
        return "redirect:/edit/" + id;
    }

    @GetMapping("/profile")
    public String profileShowUserPosts(Model model) {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        List<Post> posts = userDao.findByUsername(user.getUsername()).getPosts();
        model.addAttribute("posts", posts);
        return "/posts/profile";
    }

    @RequestMapping(path = "/create", method = RequestMethod.GET)
    public String createPostForm(Model model) {
        model.addAttribute("post", new Post());
        return "/posts/create";
    }

    @PostMapping("/create")
    public String createPost(@ModelAttribute Post post) {
        System.out.println("controller reached");
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        System.out.println(user.getUsername());
//        User user = userDao.findByUsername((String) auth.getPrincipal());
        post.setUser(user);
        emailService.prepareAndSend(post, post.getTitle(), post.getBody());
        postDao.save(post);
//        resp.sendRedirect("/posts/show");
        return "redirect:/posts";
    }

    @RequestMapping(path = "/roll-dice", method = RequestMethod.GET)
    public String rollDice() {
        return "roll-dice";
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
