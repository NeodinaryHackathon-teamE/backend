package com.example.neo_backend.domain.user.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class MyPageDto {
    private String nickName;
    private String email;
    private String password;
    private int likeCount;
    private int postCount;
}
