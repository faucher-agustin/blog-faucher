package com.astropay.blogfaucher.controller;

import com.astropay.blogfaucher.exception.NotFoundException;
import com.astropay.blogfaucher.model.Post;
import com.astropay.blogfaucher.service.PostService;

import javax.validation.constraints.Min;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping (value = "/v1/blog/posts")
@Validated
public class PostController {

    private final PostService postService;

    public PostController(PostService postService) {
        this.postService = postService;
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Map<String, Object>> getAllPost(@RequestParam(required = false, defaultValue = "10") @Min(1)  Integer limit, @RequestParam(required = false, defaultValue = "0") @Min(0) Integer page) {
        List<Post> posts = postService.getAllPostsPaginated(limit, page);
        Map<String, Object> response = new HashMap<>();
        response.put("limit", limit);
        response.put("page", page);
        response.put("total", posts.size());
        response.put("posts", posts);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Post> getPostById(@PathVariable @Min(0) Long id) throws NotFoundException {
        return new ResponseEntity<>(postService.getPostById(id), HttpStatus.OK);
    }
}
