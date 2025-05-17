package com.example.neo_backend.domain.post.dto;

import com.example.neo_backend.domain.post.entity.Post;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor
@Builder
public class PostSimpleResponseDto {

    private Long postId;
    private String title;
    private String content;
    private String place;
    private Boolean status;
    private String category;
    private Long likeCount;

    public static PostSimpleResponseDto from(Post post) {
        return PostSimpleResponseDto.builder()
                .postId(post.getPostId())
                .title(post.getTitle())
                .content(post.getContent())
                .place(post.getPlace())
                .status(post.getStatus())
                .category(post.getCategory().name())
                .likeCount((long) post.getLikeList().size())
                .build();
    }

}
