package com.example.neo_backend.domain.post.service;


import com.example.neo_backend.domain.pin.entity.Pin;
import com.example.neo_backend.domain.pin.repository.PinRepository;
import com.example.neo_backend.domain.post.dto.PostRequestDto;
import com.example.neo_backend.domain.post.dto.PostResponseDto;
import com.example.neo_backend.domain.post.entity.Post;
import com.example.neo_backend.domain.post.repository.PostRepository;
import com.example.neo_backend.global.common.exception.GeneralException;
import com.example.neo_backend.global.common.status.ErrorStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class PostService {
    private final PostRepository postRepository;
//    private final UserRepository userRepository;
    private final PinRepository pinRepository;

    @Transactional
    public PostResponseDto createPost(PostRequestDto dto) {
        try {

//            User user = userRepository.findById(dto.getUserId())
//                    .orElseThrow(() -> new GeneralException(ErrorStatus._NOT_FOUND, "해당 유저를 찾을 수 없습니다."));

            Pin pin = Pin.builder()
                    .latitude(dto.getLatitude())
                    .longitude(dto.getLongitude())
                    .build();


            Post post = Post.builder()
//                    .user(user)
                    .pin(pin)
                    .title(dto.getTitle())
                    .content(dto.getContent())
                    .place(dto.getPlace())
                    .status(false)
                    .category(dto.getCategory())
                    .build();

            Post savedPost = postRepository.save(post);
            return new PostResponseDto(savedPost.getPostId());

        } catch (GeneralException e) {
            throw e;
        } catch (Exception e) {
            e.printStackTrace();
            throw new GeneralException(ErrorStatus._POST_INTERNAL_SERVER_ERROR);
        }
    }
}