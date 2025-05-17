package com.example.neo_backend.domain.user.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class SigninDTO {
    private String email;
    private String password;
}
