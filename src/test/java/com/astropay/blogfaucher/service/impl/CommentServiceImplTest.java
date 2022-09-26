package com.astropay.blogfaucher.service.impl;

import com.astropay.blogfaucher.exception.NotFoundException;
import com.astropay.blogfaucher.model.Comment;
import com.astropay.blogfaucher.model.Post;
import com.astropay.blogfaucher.repository.CommentRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CommentServiceImplTest {

    @Mock
    private CommentRepository commentRepository;
    @InjectMocks
    private CommentServiceImpl commentService;

    @Test
    void testGetCommentsByPostIdShouldReturnAListOfPosts() throws NotFoundException {
        List<Comment> comments = List.of(new Comment(1L, "Comentario 1", "Este es el comentario 1", "email@email", 1L),
                new Comment(2L, "Comentario 2", "Este es el comentario 2", "test-mail@gmail.com", 1L));

        when(commentRepository.findByPostId(1L)).thenReturn(comments);

        List<Comment> actual = commentService.getCommentsByPostId(1L);
        assertEquals(actual, comments);
        verify(commentRepository, times(1)).findByPostId(1L);
    }

    @Test
    void testGetCommentsByPostIdShouldThrowNotFoundExceptionWhenNoPostFound() {
        when(commentRepository.findByPostId(1L)).thenReturn(new ArrayList<>());

        assertThrows(NotFoundException.class, () -> commentService.getCommentsByPostId(1L));
        verify(commentRepository, times(1)).findByPostId(1L);
    }
}