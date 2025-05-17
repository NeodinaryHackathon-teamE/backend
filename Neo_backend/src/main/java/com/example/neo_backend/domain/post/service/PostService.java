package com.example.neo_backend.domain.post.service;


import com.example.neo_backend.domain.like.repository.LikesRepository;
import com.example.neo_backend.domain.pin.entity.Pin;
import com.example.neo_backend.domain.pin.repository.PinRepository;
import com.example.neo_backend.domain.post.dto.PostRequestDto;
import com.example.neo_backend.domain.post.dto.PostResponseDto;
import com.example.neo_backend.domain.post.entity.Post;
import com.example.neo_backend.domain.post.repository.PostRepository;
import com.example.neo_backend.domain.user.entity.User;
import com.example.neo_backend.domain.user.repository.UserRepository;
import com.example.neo_backend.global.common.exception.GeneralException;
import com.example.neo_backend.global.common.status.ErrorStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class PostService {
    private final PostRepository postRepository;
    private final UserRepository userRepository;
    private final PinRepository pinRepository;
    private final LikesRepository likesRepository;

    @Transactional
    public PostResponseDto createPost(PostRequestDto dto) {
        try {

            User user = userRepository.findById(dto.getUserId())
                    .orElseThrow(() -> new GeneralException(ErrorStatus._NOT_FOUND, "해당 유저를 찾을 수 없습니다."));

            Pin pin = Pin.builder()
                    .latitude(dto.getLatitude())
                    .longitude(dto.getLongitude())
                    .build();


            Post post = Post.builder()
                    .user(user)
                    .pin(pin)
                    .title(dto.getTitle())
                    .content(dto.getContent())
                    .place(dto.getPlace())
                    .status(false)
                    .category(dto.getCategory())
                    .build();

            Post savedPost = postRepository.save(post);

            return PostResponseDto.builder()
                    .postId(savedPost.getPostId())
                    .title(savedPost.getTitle())
                    .content(savedPost.getContent())
                    .place(savedPost.getPlace())
                    .status(savedPost.getStatus())
                    .category(savedPost.getCategory().toString())
                    .likeCount(0L) //처음엔 좋아요 0개
                    .build();

        } catch (GeneralException e) {
            throw e;
        } catch (Exception e) {
            e.printStackTrace();
            throw new GeneralException(ErrorStatus._POST_INTERNAL_SERVER_ERROR);
        }
    }


    @Transactional
    public PostResponseDto getPostDetail(Long postId) {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new GeneralException(ErrorStatus._NOT_FOUND, "게시글을 찾을 수 없습니다."));

        Long likeCount = likesRepository.countByPostPostIdAndIsLikedTrue(postId); // 👍 좋아요 개수 조회

        return PostResponseDto.builder()
                .postId(post.getPostId())
                .title(post.getTitle())
                .content(post.getContent())
                .place(post.getPlace())
                .status(post.getStatus())
                .category(post.getCategory().toString())
                .likeCount(likeCount)
                .build();
    }


    @Transactional
    public PostResponseDto completePost(Long postId, Long currentUserId) {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new GeneralException(ErrorStatus._NOT_FOUND, "해당 게시글을 찾을 수 없습니다."));

        // 작성자 검증
        if (!post.getUser().getUserId().equals(currentUserId)) {
            throw new GeneralException(ErrorStatus.NOT_AUTHOR_OF_POST);
        }

        post.complete(); //status = true로 변경

        Long likeCount = likesRepository.countByPostPostIdAndIsLikedTrue(postId);

        return new PostResponseDto(
                post.getPostId(),
                post.getTitle(),
                post.getContent(),
                post.getPlace(),
                post.getStatus(),
                post.getCategory().name(),
                likeCount
        );
    }


}