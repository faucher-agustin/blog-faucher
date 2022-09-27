package com.astropay.blogfaucher;

import com.astropay.blogfaucher.model.Comment;
import com.astropay.blogfaucher.model.Post;
import com.astropay.blogfaucher.repository.CommentRepository;
import com.astropay.blogfaucher.repository.PostRepository;

import java.util.Arrays;

import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

@Component
public class DataLoader {

    private static final String BASE_URL = "https://jsonplaceholder.typicode.com/";
    private final WebClient webClient;
    private final PostRepository postRepository;
    private final CommentRepository commentRepository;

    public DataLoader(WebClient.Builder builder,
                      PostRepository postRepository,
                      CommentRepository commentRepository) {
        webClient = builder.baseUrl(BASE_URL).build();
        this.postRepository = postRepository;
        this.commentRepository = commentRepository;
    }

    @EventListener(ApplicationReadyEvent.class)
    public void loadInitialData() {
        Post[] posts = webClient.get()
                                .uri("/posts")
                                .retrieve()
                                .bodyToMono(Post[].class).block();
        if (null != posts) {
            postRepository.saveAll(Arrays.asList(posts));
            for (Post post : posts) {
                loadComments(post.getId());
            }
        }
    }

    private void loadComments(Long postId) {
        Comment[] comments = webClient.get()
                                      .uri(String.format("/posts/%d/comments", postId))
                                      .retrieve()
                                      .bodyToMono(Comment[].class).block();

        if (comments != null) {
            Arrays.stream(comments).forEach(comment -> comment.setPostId(postId));
            commentRepository.saveAll(Arrays.asList(comments));
        }
    }
}