package com.example.neo_backend.domain.post.dto;

import lombok.AllArgsConstructor;
import com.example.neo_backend.domain.post.entity.Post;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor
@Builder
public class PostResponseDto {

    private Long postId;
    private String title;
    private String content;
    private String place;
    private Boolean status;
    private String category;
    private Long likeCount;
    private List<String> imageUrls;

    public static PostResponseDto from(Post post) {
        return PostResponseDto.builder()
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
