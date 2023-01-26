package com.codeup.spring.controller;

import com.codeup.spring.models.Post;
import com.codeup.spring.services.PostService;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@NoArgsConstructor
public class PostController {

    @Autowired
    private PostService postServ;

    public PostController(PostService postServ) {

        this.postServ = postServ;

    }

    @GetMapping("/profile")
    public String profileShowUserPosts(Model model) {

        model.addAttribute("posts", postServ.profileShowUserPosts());

        return "/posts/profile";

    }

    @PostMapping("/profile")
    public String editThis(@ModelAttribute Post post, @RequestParam(name="button") long id) {

        return "redirect:/edit/" + id;

    }

    @GetMapping("/create")
    public String createPostForm(Model model) {

        model.addAttribute("post", new Post());

        return "/posts/create";

    }

    @PostMapping("/create")
    public String createPost(@ModelAttribute Post post) {

        postServ.createPost(post);

        return "redirect:/posts";

    }

    @GetMapping("/posts")
    public String show(Model model) {

        model.addAttribute("posts", postServ.showPosts());

        return "/posts/index";

    }

    @PostMapping("/posts")
    public String create(@ModelAttribute Post post, @RequestParam(name="button") long id) {

        return "redirect:/show/" + id;

    }

    @GetMapping("/show/{id}")
    public String showById(@PathVariable long id, Model model) {

        model.addAttribute("post", postServ.showById(id));

        return "posts/show";

    }

    @GetMapping("/edit/{id}")
    public String editPostById(@PathVariable long id, Model model) {

        model.addAttribute("post", postServ.editPostById(id));

        return "posts/edit";

    }

    @PostMapping ("/edit")
    public String editPost(@ModelAttribute Post post) {

        postServ.editPost(post);

        return "redirect:/profile";

    }

    @PostMapping("/delete")
    public String deletePostById(@RequestParam (name="button") long id) {

        postServ.deletePostById(id);

        return "redirect:/profile";

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
