package com.example.neo_backend.domain.like.dto;

import lombok.Getter;

@Getter
public class LikeRequestDto {
    private Long postId;
    private Long userId;
    private Boolean isLiked;
}