package com.example.neo_backend.domain.post.dto;


import com.example.neo_backend.global.common.enums.Category;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PostRequestDto {
//    private Long userId;
    private String title;
    private String content;
    private String place;
    private Category category;
    private Double latitude;
    private Double longitude;
}
