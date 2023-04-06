package com.example.like.app;

import com.example.like.dao.LikeRepository;
import com.example.like.dao.PostRepository;
import com.example.like.domain.Post;
import com.example.like.dto.PostDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

@Service
@RequiredArgsConstructor
public class PostService {

    private final PostRepository postRepository;
    private final LikeRepository likeRepository;

    @Transactional(readOnly = true)
    public PostDTO findById(Long id, boolean useColumn) {
        Post post = postRepository.findById(id)
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Post not found"));

        if (useColumn) {
            return post.toResponse();
        } else {
            return post.toResponse(likeRepository.countByPost(post));
        }
    }
    @Transactional(readOnly = true)
    public Page<PostDTO> findAll(Pageable pageable, boolean useColumn) {
        Page<Post> posts = postRepository.findAll(pageable);

        if (useColumn) {
            return posts.map(Post::toResponse);
        } else {
            return posts.map(p -> p.toResponse(likeRepository.countByPost(p)));
        }
    }
}
