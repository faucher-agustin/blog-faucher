package com.astropay.blogfaucher.controller;

import com.astropay.blogfaucher.model.Post;
import com.astropay.blogfaucher.service.PostService;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
public class PostController {

    private final PostService postService;

    public PostController(PostService postService) {
        this.postService = postService;
    }

    @GetMapping(value = "/v1/blog/posts")
    public ResponseEntity<Map<String, Object>> getAllPost(@RequestParam(required = false, defaultValue = "10") Integer limit, @RequestParam(required = false, defaultValue = "0") Integer page) {
        List<Post> posts = postService.getAllPostsPaginated(limit, page);
        Map<String, Object> response = new HashMap<>();
        response.put("limit", limit);
        response.put("page", page);
        response.put("total", posts.size());
        response.put("posts", posts);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
