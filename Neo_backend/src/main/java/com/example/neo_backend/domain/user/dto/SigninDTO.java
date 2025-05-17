package com.example.neo_backend.domain.user.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class SigninDTO {
    private String email;
    private String password;
}
