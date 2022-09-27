package com.astropay.blogfaucher.controller;

import com.astropay.blogfaucher.exception.NotFoundException;
import com.astropay.blogfaucher.model.Comment;
import com.astropay.blogfaucher.model.Post;
import com.astropay.blogfaucher.service.CommentService;
import com.astropay.blogfaucher.service.PostService;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping (value = "/v1/blog/posts")
@Validated
public class BlogController {

    private final PostService postService;
    private final CommentService commentService;

    public BlogController(PostService postService, CommentService commentService) {
        this.postService = postService;
        this.commentService = commentService;
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Map<String, Object>> getAllPost(@RequestParam(required = false, defaultValue = "10") @Min(value = 1, message = "limit must be greater than 0")  Integer limit,
                                                          @RequestParam(required = false, defaultValue = "0") @Min(value = 0, message = "page must be greater than, or equals to 0") Integer page) {
        List<Post> posts = postService.getAllPostsPaginated(limit, page);
        Map<String, Object> response = new HashMap<>();
        response.put("limit", limit);
        response.put("page", page);
        response.put("total", posts.size());
        response.put("posts", posts);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Post> getPostById(@PathVariable @Min(value = 0, message = "Post Id must be greater than, or equal to 0") Long id) throws NotFoundException {
        return new ResponseEntity<>(postService.getPostById(id), HttpStatus.OK);
    }

    @GetMapping(value = "/{id}/comments", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<Comment>> getCommentsByPostId(@PathVariable @Min(value = 0, message = "Post Id must be greater than, or equal to 0") Long id) throws NotFoundException {
        return new ResponseEntity<>(commentService.getCommentsByPostId(id), HttpStatus.OK);
    }

    @GetMapping(value = "/search", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<Post>> getPostByTitle(@RequestParam() @NotBlank String title) throws NotFoundException {
        return new ResponseEntity<>(postService.getPostsByTitle(title), HttpStatus.OK);
    }
}
