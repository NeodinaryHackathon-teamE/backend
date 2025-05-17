package com.example.neo_backend.domain.like.service;


import com.example.neo_backend.domain.like.entity.Likes;
import com.example.neo_backend.domain.like.repository.LikesRepository;
import com.example.neo_backend.domain.post.entity.Post;
import com.example.neo_backend.domain.post.repository.PostRepository;
import com.example.neo_backend.domain.user.entity.User;
import com.example.neo_backend.global.common.exception.GeneralException;
import com.example.neo_backend.global.common.status.ErrorStatus;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class LikesService {

    private final LikesRepository likesRepository;

    private final PostRepository postRepository;

    @Transactional
    public Long createLike(Long postId, HttpServletRequest request) {

        HttpSession session = request.getSession(false);
        // 로그인한 유저 불러옴
        User user = (User)session.getAttribute("user");
        log.info("로그인한 유저: {}", user.getEmail());
        // 로그인한 유저가 없을 경우
        if(user==null){
            throw new GeneralException(ErrorStatus._UNAUTHORIZED);
        }

        // 해당 제보글에 이미 좋아요 눌렀는지 확인
        boolean isLiked=false;
        // 좋아요 레코드 조회
        Likes likeRecord = likesRepository.findByPostPostIdAndUserUserId(postId, user.getUserId())
                .orElse(null);
        // 좋아요 레코드가 null인 경우 추가
        if (likeRecord == null) {
            log.info("좋아요 레코드가 존재하지 않음, 새로 생성합니다.");
            // 좋아요 레코드 생성
            Optional<Post> post = postRepository.findByPostId(postId);
            Likes newLike = Likes.builder()
                    .post(post.get())
                    .user(user)
                    .pin(post.get().getPin())
                    .isLiked(true)
                    .build();
            likesRepository.save(newLike);

        } else if(likeRecord.getIsLiked() == false) {
            // 좋아요 레코드가 존재하지만 isLiked가 false인 경우
            log.info("좋아요 레코드가 존재하지만 isLiked가 false입니다.");
            likeRecord.doLike();
            likesRepository.save(likeRecord);
        } else {
            // 이미 좋아요를 눌렀다면 좋아요 취소
            log.info("이미 좋아요를 눌렀습니다. 좋아요를 취소합니다.");
            likeRecord.cancleLike();
        }

        return likesRepository.countByPostPostIdAndIsLikedTrue(postId);
    }
}
