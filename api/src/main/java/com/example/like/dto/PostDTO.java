package com.example.like.dto;

public record PostDTO(
    Long id,
    String title,
    String content,
    String author,
    Long likeCount
) {
}
