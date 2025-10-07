package com.nurul.blog.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.nurul.blog.DTO.PostDto;
import com.nurul.blog.entity.Category;
import com.nurul.blog.entity.Post;
import com.nurul.blog.entity.User;
import com.nurul.blog.service.MailService;
import com.nurul.blog.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.MailException;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/post")
@CrossOrigin
public class PostController {

    @Autowired
    private PostService postService;

    @Autowired
    private MailService mailService;

    @GetMapping("")
    public ResponseEntity<?> getAllPosts() {
        try {

            List<PostDto> posts = postService.getAlPosts();
            Map<String, List<PostDto>> response = new HashMap<>();
            response.put("data", posts);
            if (posts.isEmpty()) {
                return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
            }
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_GATEWAY);
        }
    }

    @PostMapping("")
    public ResponseEntity<String> savePost(@RequestPart("file") MultipartFile file, @RequestPart("post") String postJson) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            Post post = objectMapper.readValue(postJson, Post.class);
            postService.storePost(post, file);
            return new ResponseEntity<>("Post stored successfully", HttpStatus.OK);

        } catch (Exception e) {
            return new ResponseEntity<>("Something went wrong" + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getById(@PathVariable Integer id) {
        try {
            Optional<PostDto> post = postService.getById(id);
            if (post.isPresent()) {
                return new ResponseEntity<>(post, HttpStatus.OK);
            }
            return new ResponseEntity<>("Post Not found", HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/save")
    public ResponseEntity<?> savePost(@RequestParam("title") String title,
                                      @RequestParam("author") String author,
                                      @RequestParam("description") String description,
                                      @RequestParam("status") String status,
                                      @RequestParam("category_id") Integer category_id,
                                      @RequestParam("user_id") Long user_id,
                                      @RequestParam(value = "file", required = false) MultipartFile file) {
        try {
            Post post = new Post();
            post.setTitle(title);
            post.setAuthor(author);
            post.setDescription(description);
            post.setStatus(Post.Status.valueOf(status));

            Category category = new Category();
            category.setId(category_id);
            post.setCategory(category);
            User user = new User();
            user.setId(user_id);
            post.setUser(user);

            Post saved = postService.create(post, file);

            try {
                mailService.sendMail(
                        "nurulcse09@gmail.com",
                        "New Post Created",
                        "A new post titled '" + title + "' has been created."
                );
            } catch (MailException e) {
                System.err.println("Failed to send email: " + e.getMessage());
            }
            return new ResponseEntity<>(saved, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
