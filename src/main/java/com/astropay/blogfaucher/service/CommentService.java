package com.astropay.blogfaucher.service;

import com.astropay.blogfaucher.exception.NotFoundException;
import com.astropay.blogfaucher.model.Comment;

import java.util.List;

public interface CommentService {
    List<Comment> getCommentsByPostId(Long postId) throws NotFoundException;
}
