package com.astropay.blogfaucher.controller;

import com.astropay.blogfaucher.model.Post;
import com.astropay.blogfaucher.service.PostService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class PostController {

    private PostService postService;

    public PostController(PostService postService) {
        this.postService = postService;
    }

    @GetMapping(value = "/v1/blog/posts")
    public ResponseEntity<List<Post>> getAllPost(@RequestParam(required = false) Integer limit, @RequestParam(required = false) Integer offset) {
        return new ResponseEntity<>(postService.getAllPostsPaginated(limit, offset), HttpStatus.OK);
    }

    @GetMapping()
    public ResponseEntity<String> check() {
        return new ResponseEntity<>("OK", HttpStatus.OK);
    }
}
