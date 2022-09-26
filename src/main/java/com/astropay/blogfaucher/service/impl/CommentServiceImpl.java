package com.astropay.blogfaucher.service.impl;

import com.astropay.blogfaucher.exception.NotFoundException;
import com.astropay.blogfaucher.model.Comment;
import com.astropay.blogfaucher.repository.CommentRepository;
import com.astropay.blogfaucher.service.CommentService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CommentServiceImpl implements CommentService {

    private final CommentRepository commentRepository;

    public CommentServiceImpl(CommentRepository commentRepository) {
        this.commentRepository = commentRepository;
    }

    @Override
    public List<Comment> getCommentsByPostId(Long postId) throws NotFoundException {
        return commentRepository.findByPostId(postId).get();
    }
}
