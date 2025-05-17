package com.example.neo_backend.domain.user.controller;

import com.example.neo_backend.domain.user.dto.SigninDTO;
import com.example.neo_backend.domain.user.dto.SignupDto;
import com.example.neo_backend.domain.user.service.AuthService;
import com.example.neo_backend.global.common.annotation.ApiErrorCodeExample;
import com.example.neo_backend.global.common.status.ErrorStatus;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/auth")
@Tag(name = "Auth", description = "인증 관련 API")
@Slf4j
public class AuthApiController {

    private final AuthService authService;

    @PostMapping("/signup")
    @Operation(summary = "회원가입", description = "이메일, 비밀번호, 닉네임으로 회원가입을 진행합니다.")
    @ApiErrorCodeExample(ErrorStatus.class)
    public ResponseEntity<String> signup(@RequestBody SignupDto signupDTO) {
        authService.signup(signupDTO);
        return ResponseEntity.ok("회원가입이 완료되었습니다.");
    }

    @PostMapping("/signin")
    @Operation(summary = "로그인", description = "아이디, 비밀번호로 로그인을 진행합니다.")
    @ApiErrorCodeExample(ErrorStatus.class)
    public ResponseEntity<String> signin(@RequestBody SigninDTO signinDTO, HttpServletRequest request) {
        authService.signin(signinDTO, request);
        return ResponseEntity.ok("로그인이 완료되었습니다.");
    }

    @GetMapping("/mypage")
    @Operation(summary = "내 정보 보기", description = "닉네임, 이메일, 내가 누른 좋아요 개수 및 나의 제보 개수 조회")
    @ApiErrorCodeExample(ErrorStatus.class)
    public ResponseEntity<?> mypage(HttpServletRequest request) {
        return authService.getMyPage(request);
    }
}
