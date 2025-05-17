package com.example.neo_backend.domain.user.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class SignupDto {
    private String email;
    private String password;
    private String nickname;
}
