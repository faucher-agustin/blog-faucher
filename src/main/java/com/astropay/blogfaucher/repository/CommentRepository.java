package com.astropay.blogfaucher.repository;

import com.astropay.blogfaucher.model.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface CommentRepository extends JpaRepository<Comment, Long> {


    Optional<List<Comment>> findByPostId(Long postId);
}
