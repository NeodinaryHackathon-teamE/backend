package com.example.neo_backend.domain.user.controller;

import com.example.neo_backend.domain.user.dto.AuthDto;
import com.example.neo_backend.domain.user.service.AuthService;
import com.example.neo_backend.global.common.response.ApiResponse;
import com.example.neo_backend.global.common.status.SuccessStatus;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/auth")
@Tag(name = "Auth", description = "인증 관련 API")
public class AuthApiController {

    private final AuthService authService;

    @PostMapping("/signup")
    @Operation(summary = "회원가입", description = "이메일, 비밀번호, 닉네임으로 회원가입을 진행합니다.")
    public ResponseEntity<ApiResponse> signup(@RequestBody AuthDto authDTO) {
        authService.signup(authDTO);
        return ApiResponse.onSuccess(SuccessStatus._OK);    }
}
