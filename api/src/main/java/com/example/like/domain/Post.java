package com.example.like.domain;

import com.example.like.dto.PostDTO;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class Post {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "title", nullable = false)
    private String title;

    @Column(name = "content", nullable = false)
    private String content;

    @Column(name = "like_count", nullable = false)
    private Long likeCount;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @OneToMany(mappedBy = "post", fetch = FetchType.LAZY)
    private List<PostUserLike> likes = new ArrayList<>();

    public PostDTO toResponse(Long likeCount) {
        return new PostDTO(
            id,
            title,
            content,
            user.getUsername(),
            likeCount
        );
    }

    public PostDTO toResponse() {
        return new PostDTO(
                id,
                title,
                content,
                user.getUsername(),
                likeCount
        );
    }
}
