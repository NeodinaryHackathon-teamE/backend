package com.example.neo_backend.domain.user.service;

import com.example.neo_backend.domain.user.dto.SigninDTO;
import com.example.neo_backend.domain.user.dto.SignupDto;
import com.example.neo_backend.domain.user.entity.User;
import com.example.neo_backend.domain.user.repository.UserRepository;
import com.example.neo_backend.global.common.response.ApiResponse;
import com.example.neo_backend.global.common.status.ErrorStatus;
import com.example.neo_backend.global.common.status.SuccessStatus;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    public ResponseEntity<ApiResponse> signup(SignupDto signupDTO) {
        // 이메일 중복 확인
        if (userRepository.findByEmail(signupDTO.getEmail()) != null) {
            return ApiResponse.onFailure(ErrorStatus.DUPLICATED_EMAIL);
        }

        User user = User.builder()
                .email(signupDTO.getEmail())
                .password(bCryptPasswordEncoder.encode(signupDTO.getPassword()))
                .nickName(signupDTO.getNickname())
                .build();

        userRepository.save(user);
        return ApiResponse.onSuccess(SuccessStatus._OK);
    }

    public ResponseEntity<ApiResponse> signin(SigninDTO signinDTO, HttpServletRequest request) {
        // 해당하는 유저 있는지 확인
        User user = userRepository.findByEmail(signinDTO.getEmail());
        if (user == null) {
            return ApiResponse.onFailure(ErrorStatus.NOT_FOUND_USER);
        }
        // 비밀번호 확인
        if (!bCryptPasswordEncoder.matches(signinDTO.getPassword(), user.getPassword())) {
            return ApiResponse.onFailure(ErrorStatus.INVALID_PASSWORD);
        }

        // 로그인 성공
        // 로그인 성공 - 세션에 사용자 정보 저장
        HttpSession session = request.getSession();
        session.setAttribute("user", user);

        // 필요한 경우 세션 타임아웃 설정
        session.setMaxInactiveInterval(3600); // 1시간

        return ApiResponse.onSuccess(SuccessStatus._OK);
    }
}
