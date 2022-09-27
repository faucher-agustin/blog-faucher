package com.astropay.blogfaucher.service.impl;

import com.astropay.blogfaucher.exception.NotFoundException;
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
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertIterableEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class PostServiceImplTest {

    @Mock
    private PostRepository postRepository;

    @InjectMocks
    private PostServiceImpl postService;

    @Test
    void testGetAllPostsPaginatedShouldReturnAListOfPosts() {
        List<Post> posts = List.of(new Post(1L, "title 1", "Este es un body", 1L),
                                   new Post(2L, "title 2", "Este es otro body", 1L));
        Page<Post> page= mock(Page.class);
        when(postRepository.findAll(PageRequest.of(1, 1))).thenReturn(page);
        when(page.getContent()).thenReturn(posts);

        List<Post> postsActual = postService.getAllPostsPaginated(1,1);

        assertIterableEquals(posts, postsActual);
    }

    @Test
    void testGetAllPostsPaginatedShouldReturnAnEmptyList() {
        Page<Post> page= mock(Page.class);
        when(postRepository.findAll(PageRequest.of(0, 1))).thenReturn(page);
        when(page.getContent()).thenReturn(new ArrayList<>());

        List<Post> postsActual = postService.getAllPostsPaginated(1,0);

        assertIterableEquals(new ArrayList<>(), postsActual);
    }
    @Test
    void testGetPostByIdShouldReturnAPost() throws NotFoundException {
        Post post = new Post(1L, "titulo", "body", 1L);
        when(postRepository.findById(1L)).thenReturn(Optional.of(post));

        Post actual = postService.getPostById(1L);

        assertEquals(post, actual);
        verify(postRepository, times(1)).findById(1L);
    }

    @Test
    void testGetPostByIdShouldThrowNotFoundExceptionWhenPostDoesNotExists() throws NotFoundException {
        when(postRepository.findById(1L)).thenReturn(Optional.ofNullable(null));

        assertThrows(NotFoundException.class, () -> postService.getPostById(1L));

        verify(postRepository, times(1)).findById(1L);
    }

    @Test
    void testGetPostByTitleShouldReturnAListOfPosts() throws NotFoundException {
        List<Post> posts = List.of(new Post(1L, "title 1", "Este es un body", 1L),
                                   new Post(2L, "title 2", "Este es otro body", 1L));
        when(postRepository.findByTitleContaining("title")).thenReturn(posts);

        List<Post> actual = postService.getPostsByTitle("title");

        assertEquals(posts, actual);
        verify(postRepository, times(1)).findByTitleContaining("title");
    }
    @Test
    void testGetPostByTitleShouldThrowNotFoundExceptionWhenNoPostFound() throws NotFoundException {
        List<Post> posts = new ArrayList<>();
        when(postRepository.findByTitleContaining("title")).thenReturn(posts);

        assertThrows(NotFoundException.class, () -> postService.getPostsByTitle("title"));
        verify(postRepository, times(1)).findByTitleContaining("title");
    }
}
