package com.example.neo_backend.domain.post.dto;

import com.example.neo_backend.domain.post.entity.Post;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class PostResponseDto {

    private Long postId;
    private String title;
    private String content;
    private String place;
    private String status;
    private String category;
    private int likeCount;

    public static PostResponseDto from(Post post) {
        return PostResponseDto.builder()
                .postId(post.getPostId())
                .title(post.getTitle())
                .content(post.getContent())
                .place(post.getPlace())
                .status(post.getStatus() ? "완료" : "대기")
                .category(post.getCategory().name())
                .likeCount(post.getLikeList().size())
                .build();
    }
}
