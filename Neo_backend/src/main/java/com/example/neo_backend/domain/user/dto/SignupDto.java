package com.example.neo_backend.domain.user.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class SignupDto {
    private String email;
    private String password;
    private String nickname;
}
