package com.example.neo_backend.domain.user.service;

import com.example.neo_backend.domain.like.repository.LikesRepository;
import com.example.neo_backend.domain.post.dto.PostResponseDto;
import com.example.neo_backend.domain.post.repository.PostRepository;
import com.example.neo_backend.domain.user.entity.User;
import com.example.neo_backend.global.common.exception.GeneralException;
import com.example.neo_backend.global.common.status.ErrorStatus;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {

    private final PostRepository postRepository;
    private final LikesRepository likesRepository;

    public List<PostResponseDto> getLikedPost(HttpServletRequest request) {
        // 로그인한 유저 불러옴
        HttpSession session = request.getSession(false);
        User user = (User)session.getAttribute("user");

        // 로그인한 유저가 없을 경우
        if (user == null) {
            throw new GeneralException(ErrorStatus._UNAUTHORIZED);
        }
        // 해당 유저가 좋아요 누른 제보글 목록 조회
        List<Long> postIds = likesRepository.findLikedPostIdsByUserId(user.getUserId());
        List<PostResponseDto> likedPosts = new ArrayList<>();
        // 좋아요 누른 제보글 목록을 PostResponseDto로 변환
        for (Long postId : postIds) {
            postRepository.findByPostId(postId).ifPresent(post -> {
                PostResponseDto postResponseDto = PostResponseDto.builder()
                        .postId(post.getPostId())
                        .title(post.getTitle())
                        .content(post.getContent())
                        .place(post.getPlace())
                        .status(post.getStatus())
                        .category(post.getCategory().toString())
                        .likeCount(likesRepository.countByPostPostIdAndIsLikedTrue(postId))
                        .build();
                likedPosts.add(postResponseDto);
            });
        }

        return likedPosts;
    }
}
