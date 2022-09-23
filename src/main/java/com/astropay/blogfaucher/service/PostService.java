package com.astropay.blogfaucher.service;

import com.astropay.blogfaucher.model.Post;

import java.util.List;

public interface PostService {
    public List<Post> getAllPostsPaginated(int limit, int offset);
}
