package com.astropay.blogfaucher.controller;

import com.astropay.blogfaucher.exception.NotFoundException;
import com.astropay.blogfaucher.utils.ParserUtils;
import com.astropay.blogfaucher.model.Post;
import com.astropay.blogfaucher.service.PostService;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;

import javax.validation.ConstraintViolationException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(PostController.class)
class PostControllerTest {

    @MockBean
    private PostService postService;

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
                .andExpect(content().json(ParserUtils.toJsonString(response.getBody())));

        verify(postService, times(1)).getAllPostsPaginated(2,0);

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
                .andExpect(content().json(ParserUtils.toJsonString(response.getBody())));
        verify(postService, times(1)).getAllPostsPaginated(10,0);
    }

    @Test
    void testGetAllPostPaginatedWhenAParamIsNegativeAndThrowBadRequestException() throws Exception {
        Map<String, Object> exceptionBody = new HashMap<>();
        exceptionBody.put("message", "getAllPost.limit: must be greater than or equal to 1");
        exceptionBody.put("status" , HttpStatus.BAD_REQUEST.toString());
        ResponseEntity<String> response = ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ParserUtils.toJsonString(exceptionBody));
            mvc.perform(get("/v1/blog/posts?limit=-2"))
                        .andExpect(status().is4xxClientError())
                        .andExpect(content().json(response.getBody()));
        verifyNoInteractions(postService);
    }

    @Test
    void testGetPostByIdShouldReturnAPost() throws Exception {
        Post post = new Post(1L, "titulo", "body", 1L);
        when(postService.getPostById(1L)).thenReturn(post);
        mvc.perform(get("/v1/blog/posts/1"))
                .andExpect(status().isOk())
                .andExpect(content().json(ParserUtils.toJsonString(post.toMap())));

        verify(postService, times(1)).getPostById(1L);
    }

    @Test
    void testGetPostByIdWhenPostDoesNotExistsShouldReturnNotFound() throws Exception {
        when(postService.getPostById(1L)).thenThrow(new NotFoundException("Post not found."));
        Map<String, Object> exceptionBody = new HashMap<>();
        exceptionBody.put("message","Post not found.");
        exceptionBody.put("status" , HttpStatus.NOT_FOUND.toString());
        mvc.perform(get("/v1/blog/posts/1"))
                .andExpect(status().is4xxClientError())
                .andExpect(content().json(ParserUtils.toJsonString(exceptionBody)));

        verify(postService, times(1)).getPostById(1L);
    }

    @Test
    void testGetPostByIdWhenIdParamIsNegativeAndThrowBadRequestException() throws Exception {
        Map<String, Object> exceptionBody = new HashMap<>();
        exceptionBody.put("message", "getPostById.id: must be greater than or equal to 0");
        exceptionBody.put("status" , HttpStatus.BAD_REQUEST.toString());
        ResponseEntity<String> response = ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ParserUtils.toJsonString(exceptionBody));
        mvc.perform(get("/v1/blog/posts/-2"))
                .andExpect(status().is4xxClientError())
                .andExpect(content().json(response.getBody()));
        verifyNoInteractions(postService);
    }
}