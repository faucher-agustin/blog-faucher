package com.astropay.blogfaucher.service;

import com.astropay.blogfaucher.exception.NotFoundException;
import com.astropay.blogfaucher.model.Post;

import java.util.List;

public interface PostService {
    List<Post> getAllPostsPaginated(int limit, int offset);
    Post getPostById(Long id) throws NotFoundException;
}
