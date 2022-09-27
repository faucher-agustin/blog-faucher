package com.astropay.blogfaucher.service.impl;

import com.astropay.blogfaucher.exception.NotFoundException;
import com.astropay.blogfaucher.model.Comment;
import com.astropay.blogfaucher.repository.CommentRepository;
import com.astropay.blogfaucher.service.CommentService;

import java.util.List;

import org.springframework.stereotype.Service;


@Service
public class CommentServiceImpl implements CommentService {

    private final CommentRepository commentRepository;

    public CommentServiceImpl(CommentRepository commentRepository) {
        this.commentRepository = commentRepository;
    }

    @Override
    public List<Comment> getCommentsByPostId(Long postId) throws NotFoundException {
        List<Comment> comments = commentRepository.findByPostId(postId);
        if (comments.isEmpty()) {
            throw new NotFoundException(String.format("Comments not found for post %s", postId));
        }
        return comments;
    }
}
