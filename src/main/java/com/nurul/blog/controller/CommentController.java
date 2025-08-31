package com.nurul.blog.controller;

import com.nurul.blog.entity.Comment;
import com.nurul.blog.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/comments")

public class CommentController {

    @Autowired
    private CommentService commentService;

    public ResponseEntity<?> getComments() {
        try {
            List<Comment> comments = commentService.getComments();
            if (comments.isEmpty()) {
                return new ResponseEntity<>("Comments not found", HttpStatus.NOT_FOUND);
            }
            return new ResponseEntity<>(comments, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }
}
