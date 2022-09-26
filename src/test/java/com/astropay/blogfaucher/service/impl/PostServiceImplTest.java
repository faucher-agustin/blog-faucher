package com.astropay.blogfaucher.service.impl;

import com.astropay.blogfaucher.model.Post;
import com.astropay.blogfaucher.repository.PostRepository;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertIterableEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class PostServiceImplTest {

    @Mock
    private PostRepository postRepository;

    @InjectMocks
    private PostServiceImpl postService;

    @Test
    void getAllPostsPaginatedShouldReturnAListOfPosts() {
        List<Post> posts = List.of(new Post(1L, "title 1", "Este es un body", 1L),
                                   new Post(2L, "title 2", "Este es otro body", 1L));
        Page<Post> page= mock(Page.class);
        when(postRepository.findAll(PageRequest.of(1, 1))).thenReturn(page);
        when(page.getContent()).thenReturn(posts);

        List<Post> postsActual = postService.getAllPostsPaginated(1,1);

        assertIterableEquals(posts, postsActual);
    }

    @Test
    void getAllPostsPaginatedShouldReturnAnEmptyList() {
        Page<Post> page= mock(Page.class);
        when(postRepository.findAll(PageRequest.of(0, 1))).thenReturn(page);
        when(page.getContent()).thenReturn(new ArrayList<>());

        List<Post> postsActual = postService.getAllPostsPaginated(1,0);

        assertIterableEquals(new ArrayList<>(), postsActual);
    }
}