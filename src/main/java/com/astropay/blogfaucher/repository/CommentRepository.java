package com.astropay.blogfaucher.repository;

import com.astropay.blogfaucher.model.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentRepository extends JpaRepository<Comment, Long> {
}
