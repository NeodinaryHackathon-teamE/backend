package com.example.neo_backend.domain.like.service;


import com.example.neo_backend.domain.like.entity.Likes;
import com.example.neo_backend.domain.like.repository.LikesRepository;
import com.example.neo_backend.domain.post.repository.PostRepository;
import com.example.neo_backend.domain.user.entity.User;
import com.example.neo_backend.global.common.exception.GeneralException;
import com.example.neo_backend.global.common.status.ErrorStatus;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class LikesService {

    private final LikesRepository likesRepository;

    private final PostRepository postRepository;

    @Transactional
    public void createLike(Long postId, HttpServletRequest request) {

        HttpSession session = request.getSession(false);
        // 로그인한 유저 불러옴
        User user = (User)session.getAttribute("user");

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
            // 좋아요 레코드 생성
            Likes newLike = Likes.builder()
                    .post(postRepository.findByPostId(postId))
                    .user(user)
                    .isLiked(true)
                    .build();
            likesRepository.save(newLike);

        } else if(likeRecord.getIsLiked() == false) {
            // 좋아요 레코드가 존재하지만 isLiked가 false인 경우
            likeRecord.doLike();
            likesRepository.save(likeRecord);
        } else {
            // 이미 좋아요를 눌렀다면 좋아요 취소
            likeRecord.cancleLike();
        }


    }
}
