package com.example.neo_backend.domain.post.controller;

import com.example.neo_backend.domain.post.dto.PostRequestDto;
import com.example.neo_backend.domain.post.dto.PostResponseDto;
import com.example.neo_backend.domain.post.service.PostService;
import com.example.neo_backend.domain.user.entity.User;
import com.example.neo_backend.domain.user.repository.UserRepository;
import com.example.neo_backend.global.common.exception.GeneralException;
import com.example.neo_backend.global.common.status.ErrorStatus;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.Enumeration;
import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/api/v1/post")
@Tag(name = "post", description = "제보글관련 API")
public class PostController {

    private final PostService postService;
    private final UserRepository userRepository;

    @PostMapping
    @Operation(summary = "제보글 작성", description = "제보글 작성 API")
    public ResponseEntity<PostResponseDto> createPost(
            @RequestPart("dto") String dtoJson,
            @RequestPart("images") List<MultipartFile> images) {

        System.out.println(images.size());

        PostRequestDto dto;

        try {
            ObjectMapper objectMapper = new ObjectMapper();
            dto = objectMapper.readValue(dtoJson, PostRequestDto.class);
        } catch (JsonProcessingException e) {
            throw new RuntimeException("JSON 파싱 실패", e);
        }

        PostResponseDto response = postService.createPost(dto, images);
        return ResponseEntity.ok(response);
    }


    @GetMapping("/{postId}")
    @Operation(summary = "제보글 상세 조회", description = "게시글 상세 정보 및 좋아요 수 조회")
    public ResponseEntity<PostResponseDto> getPostDetail(@PathVariable("postId") Long postId) {
        PostResponseDto response = postService.getPostDetail(postId);
        return ResponseEntity.ok(response);
    }

    @PutMapping("/{postId}/complete")
    @Operation(summary = "제보글 완료 처리", description = "게시글의 status를 완료로 변경")
    public ResponseEntity<PostResponseDto> completePost(@PathVariable("postId") Long postId,
                                                        HttpSession session) {

        String email = null;

        Enumeration<String> attrNames = session.getAttributeNames();
        while (attrNames.hasMoreElements()) {
            String attrName = attrNames.nextElement();
            Object attr = session.getAttribute(attrName);
            if (attr instanceof User) {
                User user = (User) attr;
                email = user.getEmail();
                break;
            }
        }

        if (email == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new GeneralException(ErrorStatus.NOT_FOUND_USER));

        Long currentUserId = user.getUserId();
        PostResponseDto response = postService.completePost(postId, currentUserId);
        return ResponseEntity.ok(response);
    }

}
