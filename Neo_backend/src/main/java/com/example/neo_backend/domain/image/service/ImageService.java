package com.example.neo_backend.domain.image.service;

import com.example.neo_backend.domain.image.entity.Image;
import com.example.neo_backend.domain.image.repository.ImageRepository;
import com.example.neo_backend.domain.image.utils.S3Uploader;
import com.example.neo_backend.domain.pin.entity.Pin;
import com.example.neo_backend.domain.post.entity.Post;
import com.example.neo_backend.domain.post.repository.PostRepository;
import com.example.neo_backend.domain.user.entity.User;
import com.example.neo_backend.global.common.status.ErrorStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ImageService {
    private final S3Uploader s3Uploader;
    private final ImageRepository imageRepository;
    private final PostRepository postRepository;

    @Transactional
    public List<Image> uploadImages(Long postId, List<MultipartFile> images, User user, Pin pin) {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new RuntimeException(
                        ErrorStatus._NOT_FOUND.getMessageOrDefault("해당하는 게시글이 없습니다.")));

        List<Image> savedImages = new ArrayList<>();

        for (MultipartFile imageFile : images) {
            try {
                System.out.println("Uploading image: " + imageFile.getOriginalFilename() + ", size: " + imageFile.getSize());
                String imageUrl = s3Uploader.uploadFiles(imageFile, "uploads");
                System.out.println("Uploaded URL: " + imageUrl);

                Image image = Image.builder()
                        .post(post)
                        .user(user)
                        .pin(pin)
                        .imageURL(imageUrl)
                        .build();

                savedImages.add(imageRepository.save(image));
            } catch (Exception e) {
                e.printStackTrace();
                throw new RuntimeException(
                        ErrorStatus._INTERNAL_SERVER_ERROR.getMessageOrDefault("이미지 업로드 실패"), e);
            }
        }

        return savedImages;
    }

}
