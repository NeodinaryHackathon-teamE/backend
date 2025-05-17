package com.example.neo_backend.domain.post.service;


import com.example.neo_backend.domain.image.entity.Image;
import com.example.neo_backend.domain.image.repository.ImageRepository;
import com.example.neo_backend.domain.image.service.ImageService;
import com.example.neo_backend.domain.image.utils.S3Uploader;
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
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PostService {
    private final PostRepository postRepository;
    private final UserRepository userRepository;
    private final ImageRepository imageRepository;
    private final LikesRepository likesRepository;
    private final S3Uploader s3Uploader;
    private final ImageService imageService;
    private final PinRepository pinRepository;

    @Transactional
    public PostResponseDto createPost(PostRequestDto dto,  List<MultipartFile> images) {
        try {

            User user = userRepository.findById(dto.getUserId())
                    .orElseThrow(() -> new GeneralException(ErrorStatus._NOT_FOUND, "해당 유저를 찾을 수 없습니다."));

            Pin pin = Pin.builder()
                    .latitude(dto.getLatitude())
                    .longitude(dto.getLongitude())
                    .build();
            Pin savedPin = pinRepository.save(pin);


            Post post = Post.builder()
                    .user(user)
                    .pin(savedPin)
                    .title(dto.getTitle())
                    .content(dto.getContent())
                    .place(dto.getPlace())
                    .status(false)
                    .category(dto.getCategory())
                    .build();

            Post savedPost = postRepository.save(post);

            List<Image> savedImages = new ArrayList<>();
            if(images != null && !images.isEmpty()) {
                savedImages = imageService.uploadImages(post.getPostId(), images, user, savedPin);
            }

            List<String> imageUrls = savedImages.stream()
                    .map(Image::getImageURL)
                    .collect(Collectors.toList());

            return PostResponseDto.builder()
                    .postId(savedPost.getPostId())
                    .title(savedPost.getTitle())
                    .content(savedPost.getContent())
                    .place(savedPost.getPlace())
                    .status(savedPost.getStatus())
                    .category(savedPost.getCategory().toString())
                    .likeCount(0L) //처음엔 좋아요 0개
                    .imageUrls(imageUrls)
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

        List<String> imageUrls = post.getImageList().stream()
                .map(Image::getImageURL)
                .collect(Collectors.toList());


        return PostResponseDto.builder()
                .postId(post.getPostId())
                .title(post.getTitle())
                .content(post.getContent())
                .place(post.getPlace())
                .status(post.getStatus())
                .category(post.getCategory().toString())
                .likeCount(likeCount)
                .imageUrls(imageUrls)
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
                likeCount,
                post.getImageUrls()
        );
    }


}