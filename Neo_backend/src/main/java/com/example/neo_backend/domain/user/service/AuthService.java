package com.example.neo_backend.domain.user.service;

import com.example.neo_backend.domain.user.dto.AuthDto;
import com.example.neo_backend.domain.user.entity.User;
import com.example.neo_backend.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    public void signup(AuthDto authDTO) {
        // 이메일 중복 확인
        if (userRepository.findByEmail(authDTO.getEmail()) != null) {
            throw new IllegalArgumentException("이미 존재하는 이메일입니다.");
        }

        User user = User.builder()
                .email(authDTO.getEmail())
                .password(bCryptPasswordEncoder.encode(authDTO.getPassword()))
                .nickName(authDTO.getNickname())
                .role("ROLE_USER")
                .build();

        userRepository.save(user);
    }
}
