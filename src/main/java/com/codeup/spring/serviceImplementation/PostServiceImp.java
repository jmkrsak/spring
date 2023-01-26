package com.codeup.spring.serviceImplementation;

import com.codeup.spring.models.Post;
import com.codeup.spring.models.User;
import com.codeup.spring.repositories.PostRepository;
import com.codeup.spring.repositories.UserRepository;
import com.codeup.spring.services.EmailService;
import com.codeup.spring.services.PostService;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PostServiceImp implements PostService {

    private final PostRepository postDao;
    private final UserRepository userDao;
    private final EmailService emailService;

    public PostServiceImp(PostRepository postDao, UserRepository userDao, EmailService emailService) {

        this.postDao = postDao;
        this.userDao = userDao;
        this.emailService = emailService;

    }

    public List<Post> profileShowUserPosts() {

        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        List<Post> posts = userDao.findByUsername(user.getUsername()).getPosts();

        return posts;

    }

    public void createPost(Post post) {

        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        post.setUser(user);
        emailService.prepareAndSend(post, post.getTitle(), post.getBody());
        postDao.save(post);

    }

    public List<Post> showPosts() {

        List<Post> posts = postDao.findAll();

        return posts;

    }

    public Post showById(long id) {

        Post post = postDao.getReferenceById(id);

        return post;

    }

    public Post editPostById(long id) {

        Post post = postDao.getReferenceById(id);

        return post;

    }

    public void editPost(Post post) {

        postDao.save(post);

    }

    public void deletePostById(long id) {

        postDao.deleteById(id);

    }

}
