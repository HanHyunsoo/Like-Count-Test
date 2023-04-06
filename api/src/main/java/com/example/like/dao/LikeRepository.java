package com.example.like.dao;

import com.example.like.domain.Post;
import com.example.like.domain.PostUserLike;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LikeRepository extends JpaRepository<PostUserLike, Long> {
    Long countByPost(Post post);
}
