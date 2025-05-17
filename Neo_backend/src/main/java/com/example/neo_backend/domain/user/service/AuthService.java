package com.example.neo_backend.domain.user.service;

import com.example.neo_backend.domain.user.dto.AuthDto;
import com.example.neo_backend.domain.user.entity.User;
import com.example.neo_backend.domain.user.repository.UserRepository;
import com.example.neo_backend.global.common.response.ApiResponse;
import com.example.neo_backend.global.common.status.ErrorStatus;
import com.example.neo_backend.global.common.status.SuccessStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    public ResponseEntity<ApiResponse> signup(AuthDto authDTO) {
        // 이메일 중복 확인
        if (userRepository.findByEmail(authDTO.getEmail()).isPresent()) {
            return ApiResponse.onFailure(ErrorStatus.DUPLICATED_EMAIL);
        }

        User user = User.builder()
                .email(authDTO.getEmail())
                .password(bCryptPasswordEncoder.encode(authDTO.getPassword()))
                .nickName(authDTO.getNickname())
                .build();

        userRepository.save(user);
        return ApiResponse.onSuccess(SuccessStatus._OK);
    }
}
