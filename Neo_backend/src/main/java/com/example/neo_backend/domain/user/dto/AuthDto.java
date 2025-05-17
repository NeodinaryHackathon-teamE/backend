package com.example.neo_backend.domain.user.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AuthDto {
    private String email;
    private String password;
    private String nickname;
}
