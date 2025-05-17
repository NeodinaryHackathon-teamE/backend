package com.example.neo_backend.domain.user.service;

import com.example.neo_backend.domain.like.repository.LikesRepository;
import com.example.neo_backend.domain.post.repository.PostRepository;
import com.example.neo_backend.domain.user.dto.MyPageDto;
import com.example.neo_backend.domain.user.dto.SigninDTO;
import com.example.neo_backend.domain.user.dto.SignupDto;
import com.example.neo_backend.domain.user.entity.User;
import com.example.neo_backend.domain.user.repository.UserRepository;
import com.example.neo_backend.global.common.exception.GeneralException;
import com.example.neo_backend.global.common.response.ApiResponse;
import com.example.neo_backend.global.common.status.ErrorStatus;
import com.example.neo_backend.global.common.status.SuccessStatus;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class AuthService {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final PostRepository postRepository;
    private final LikesRepository likesRepository;

    public ResponseEntity<ApiResponse> signup(SignupDto signupDTO) {
        // 이메일 중복 확인
        userRepository.findByEmail(signupDTO.getEmail())
                .ifPresent(user -> {
            throw new GeneralException(ErrorStatus.DUPLICATED_EMAIL);
        });
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
        User user = userRepository.findByEmail(signinDTO.getEmail())
                .orElseThrow(() -> new GeneralException(ErrorStatus.NOT_FOUND_USER));

        // 사용자가 존재하면 비밀번호 검증
        if (!bCryptPasswordEncoder.matches(signinDTO.getPassword(), user.getPassword())) {
            throw new GeneralException(ErrorStatus.INVALID_PASSWORD);
        }

        // 로그인 성공

        // 로그인 성공 - 세션에 사용자 정보 저장
        HttpSession session = request.getSession();

        session.setAttribute("user", user);


        // 필요한 경우 세션 타임아웃 설정
        session.setMaxInactiveInterval(3600); // 1시간

        return ApiResponse.onSuccess(SuccessStatus._OK);
    }

    public ResponseEntity<ApiResponse> getMyPage(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        if (session == null) throw new GeneralException(ErrorStatus._UNAUTHORIZED);

        User user = (User) session.getAttribute("user");
        if (user == null) throw new GeneralException(ErrorStatus._UNAUTHORIZED);

        int myPosts = postRepository.countByUser(user);
        int myLikes = likesRepository.countByUser(user);

        MyPageDto response = MyPageDto.builder()
                .nickName(user.getNickName())
                .email(user.getEmail())
                .postCount(myPosts)
                .likeCount(myLikes)
                .build();

        return ApiResponse.onSuccess(SuccessStatus._OK, response);
    }

}
