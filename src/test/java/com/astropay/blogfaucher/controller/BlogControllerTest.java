package com.astropay.blogfaucher.controller;

import com.astropay.blogfaucher.exception.NotFoundException;
import com.astropay.blogfaucher.model.Comment;
import com.astropay.blogfaucher.service.CommentService;
import com.astropay.blogfaucher.utils.ParserUtils;
import com.astropay.blogfaucher.model.Post;
import com.astropay.blogfaucher.service.PostService;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertIterableEquals;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(BlogController.class)
class BlogControllerTest {

    @MockBean
    private PostService postService;
    @MockBean
    private CommentService commentService;

    @Autowired
    private MockMvc mvc;

    @Test
    void testGetAllPostPaginatedWhenLimitAndPagePresentShouldReturnOk() throws Exception {
        List<Post> posts = List.of(new Post(1L, "title 1", "Este es un body", 1L),
                                   new Post(2L, "title 2", "Este es otro body", 1L));


        ResponseEntity<Map<String, Object>> response = new ResponseEntity<>(Map.of("limit", 2,
                                                                                   "page", 0,
                                                                                   "total", 2,
                                                                                   "posts", posts),
                                                                            HttpStatus.OK);
        when(postService.getAllPostsPaginated(2, 0)).thenReturn(posts);

        mvc.perform(get("/v1/blog/posts?limit=2&page=0"))
                .andExpect(status().isOk())
                .andExpect(content().json(ParserUtils.mapToJsonString(response.getBody())));

        verify(postService, times(1)).getAllPostsPaginated(2,0);
        verifyNoInteractions(commentService);

    }

    @Test
    void testGetAllPostPaginatedWhenLimitAndPageNotPresentShouldReturnOk() throws Exception {
        List<Post> posts = List.of(new Post(1L, "title 1", "Este es un body", 1L),
                                   new Post(2L, "title 2", "Este es otro body", 1L));


        ResponseEntity<Map<String, Object>> response = new ResponseEntity<>(Map.of("limit", 10,
                                                                                   "page", 0,
                                                                                   "total", 2,
                                                                                   "posts", posts),
                                                                            HttpStatus.OK);
        when(postService.getAllPostsPaginated(10, 0)).thenReturn(posts);

        mvc.perform(get("/v1/blog/posts"))
                .andExpect(status().isOk())
                .andExpect(content().json(ParserUtils.mapToJsonString(response.getBody())));
        verify(postService, times(1)).getAllPostsPaginated(10,0);
        verifyNoInteractions(commentService);
    }

    @Test
    void testGetAllPostPaginatedWhenAParamIsNegativeAndThrowBadRequestException() throws Exception {
        Map<String, Object> exceptionBody = new HashMap<>(2);
        exceptionBody.put("message", "getAllPost.limit: limit must be greater than 0");
        exceptionBody.put("status" , HttpStatus.BAD_REQUEST.toString());
        ResponseEntity<String> response = ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ParserUtils.mapToJsonString(exceptionBody));
            mvc.perform(get("/v1/blog/posts?limit=-2"))
                        .andExpect(status().is4xxClientError())
                        .andExpect(content().json(response.getBody()));
        verifyNoInteractions(postService);
        verifyNoInteractions(commentService);
    }

    @Test
    void testGetPostByIdShouldReturnAPost() throws Exception {
        Post post = new Post(1L, "titulo", "body", 1L);
        when(postService.getPostById(1L)).thenReturn(post);
        mvc.perform(get("/v1/blog/posts/1"))
                .andExpect(status().isOk())
                .andExpect(content().json(ParserUtils.mapToJsonString(post.toMap())));

        verify(postService, times(1)).getPostById(1L);
        verifyNoInteractions(commentService);
    }

    @Test
    void testGetPostByIdWhenPostDoesNotExistsShouldReturnNotFound() throws Exception {
        when(postService.getPostById(1L)).thenThrow(new NotFoundException("Post not found."));
        Map<String, Object> exceptionBody = new HashMap<>(2);
        exceptionBody.put("message","Post not found.");
        exceptionBody.put("status" , HttpStatus.NOT_FOUND.toString());
        mvc.perform(get("/v1/blog/posts/1"))
                .andExpect(status().is4xxClientError())
                .andExpect(content().json(ParserUtils.mapToJsonString(exceptionBody)));

        verify(postService, times(1)).getPostById(1L);
        verifyNoInteractions(commentService);
    }

    @Test
    void testGetPostByIdWhenIdParamIsNegativeAndThrowBadRequestException() throws Exception {
        Map<String, Object> exceptionBody = new HashMap<>(2);
        exceptionBody.put("message", "getPostById.id: Post Id must be greater than, or equal to 0");
        exceptionBody.put("status", HttpStatus.BAD_REQUEST.toString());
        ResponseEntity<String> response = ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ParserUtils.mapToJsonString(exceptionBody));
        mvc.perform(get("/v1/blog/posts/-2"))
                .andExpect(status().is4xxClientError())
                .andExpect(content().json(response.getBody()));
        verifyNoInteractions(postService);
        verifyNoInteractions(commentService);
    }

    @Test
    void testGetCommentsByPostIdShouldReturnAListOfComments () throws Exception {
        List<Comment> comments = List.of(new Comment(1L, "Comentario 1", "Este es el comentario 1", "email@email", 1L),
                                         new Comment(2L, "Comentario 2", "Este es el comentario 2", "test-mail@gmail.com", 1L));
        when(commentService.getCommentsByPostId(1L)).thenReturn(comments);
        List<String> commentList = new ArrayList<>();
        commentList.add(ParserUtils.mapToJsonString(comments.get(0).toMap()));
        commentList.add(ParserUtils.mapToJsonString(comments.get(1).toMap()));

        mvc.perform(get("/v1/blog/posts/1/comments"))
                .andExpect(status().isOk())
                .andExpect(content().json(commentList.toString()));
        verify(commentService, times(1)).getCommentsByPostId(1L);
        verifyNoInteractions(postService);
    }

    @Test
    void testGetCommentsByPostIdWhenIdParamIsNegativeAndThrowBadRequestException() throws Exception {
        Map<String, Object> exceptionBody = new HashMap<>(2);
        exceptionBody.put("message", "getCommentsByPostId.id: Post Id must be greater than, or equal to 0");
        exceptionBody.put("status" , HttpStatus.BAD_REQUEST.toString());
        ResponseEntity<String> response = ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ParserUtils.mapToJsonString(exceptionBody));
        mvc.perform(get("/v1/blog/posts/-2/comments"))
                .andExpect(status().is4xxClientError())
                .andExpect(content().json(response.getBody()));
        verifyNoInteractions(postService);
        verifyNoInteractions(commentService);
    }

    @Test
    void testCommentsByPostIdWhenCommentDoesNotExistsShouldReturnNotFoundException() throws Exception {
        when(commentService.getCommentsByPostId(1L)).thenThrow(new NotFoundException("Comments not found."));
        Map<String, Object> exceptionBody = new HashMap<>(2);
        exceptionBody.put("message","Comments not found.");
        exceptionBody.put("status" , HttpStatus.NOT_FOUND.toString());
        mvc.perform(get("/v1/blog/posts/1/comments"))
                .andExpect(status().is4xxClientError())
                .andExpect(content().json(ParserUtils.mapToJsonString(exceptionBody)));

        verify(commentService, times(1)).getCommentsByPostId(1L);
        verifyNoInteractions(postService);
    }

    @Test
    void testGetPostByTitleShouldReturnAListOfPostsContainingAGivenString () throws Exception {
        List<Post> posts = List.of(new Post(1L, "title 1", "Este es un body", 1L),
                                   new Post(2L, "title 2", "Este es otro body", 1L));
        List<String> postList = new ArrayList<>();
        postList.add(ParserUtils.mapToJsonString(posts.get(0).toMap()));
        postList.add(ParserUtils.mapToJsonString(posts.get(1).toMap()));
        when(postService.getPostsByTitle("title")).thenReturn(posts);
        mvc.perform(get("/v1/blog/posts/search?title=title"))
                .andExpect(status().isOk())
                .andExpect(content().json(postList.toString()));
        verify(postService, times(1)).getPostsByTitle("title");
        verifyNoInteractions(commentService);
    }

    @Test
    void testGetPostTitleWhenPostDoesNotExistsShouldReturnNotFound() throws Exception {
        when(postService.getPostsByTitle("s5s5s")).thenThrow(new NotFoundException("Posts not found with title containing %s"));
        Map<String, Object> exceptionBody = new HashMap<>(2);
        exceptionBody.put("message","Posts not found with title containing %s");
        exceptionBody.put("status" , HttpStatus.NOT_FOUND.toString());
        mvc.perform(get("/v1/blog/posts/search?title=s5s5s"))
                .andExpect(status().is4xxClientError())
                .andExpect(content().json(ParserUtils.mapToJsonString(exceptionBody)));

        verify(postService, times(1)).getPostsByTitle("s5s5s");
        verifyNoInteractions(commentService);
    }
}