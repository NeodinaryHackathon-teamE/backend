package com.example.neo_backend.domain.like.service;

import com.example.neo_backend.domain.like.dto.LikeRequestDto;
import com.example.neo_backend.domain.like.dto.LikeResponseDto;
import com.example.neo_backend.domain.like.entity.Likes;
import com.example.neo_backend.domain.like.repository.LikesRepository;
import com.example.neo_backend.domain.post.entity.Post;
import com.example.neo_backend.domain.post.repository.PostRepository;
import com.example.neo_backend.domain.user.entity.User;
import com.example.neo_backend.domain.user.repository.UserRepository;
import com.example.neo_backend.global.common.exception.GeneralException;
import com.example.neo_backend.global.common.status.ErrorStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class LikesService {

    private final LikesRepository likesRepository;
    private final PostRepository postRepository;
    private final UserRepository userRepository;

    @Transactional
    public LikeResponseDto likePost(LikeRequestDto dto) {
        Post post = postRepository.findById(dto.getPostId())
                .orElseThrow(() -> new GeneralException(ErrorStatus._NOT_FOUND, "게시글을 찾을 수 없습니다."));

        // Spring Security의 인증된 사용자 정보에서 이메일을 꺼냄
        String email = SecurityContextHolder.getContext().getAuthentication().getName();

        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new GeneralException(ErrorStatus._NOT_FOUND, "유저를 찾을 수 없습니다."));

        Likes likes = Likes.builder()
                .post(post)
                .user(user)
                .pin(post.getPin())
                .isLiked(dto.getIsLiked())
                .build();

        Likes savedLike = likesRepository.save(likes);
        return new LikeResponseDto(savedLike.getLikeId());
    }

}
