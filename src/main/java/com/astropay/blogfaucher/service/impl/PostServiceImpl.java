package com.astropay.blogfaucher.service.impl;

import com.astropay.blogfaucher.model.Post;
import com.astropay.blogfaucher.repository.PostRepository;
import com.astropay.blogfaucher.service.PostService;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PostServiceImpl implements PostService {

    private final PostRepository postRepository;

    public PostServiceImpl(PostRepository postRepository) {
        this.postRepository = postRepository;
    }

    @Override
    public List<Post> getAllPostsPaginated(int limit, int offset) {
        Pageable pageable = PageRequest.of(offset, limit);
        return postRepository.findAll(pageable).getContent();
    }
}
