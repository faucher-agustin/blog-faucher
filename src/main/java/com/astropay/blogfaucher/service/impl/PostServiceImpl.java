package com.astropay.blogfaucher.service.impl;

import com.astropay.blogfaucher.exception.NotFoundException;
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
    @Override
    public Post getPostById(Long id) throws NotFoundException {
        return postRepository.findById(id).orElseThrow(() -> new NotFoundException("Post not found."));
    }

    @Override
    public List<Post> getPostsByTitle(String title) throws NotFoundException {
        List<Post> posts = postRepository.findByTitleContaining(title);
        if (posts.isEmpty()) {
            throw new NotFoundException(String.format("Posts not found with title containing %s", title));
        }
        return posts;
    }
}
