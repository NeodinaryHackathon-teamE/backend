package com.example.neo_backend.domain.post.controller;

import com.example.neo_backend.global.common.annotation.ApiErrorCodeExample;
import com.example.neo_backend.global.common.enums.Category;
import com.example.neo_backend.global.common.exception.GeneralException;
import com.example.neo_backend.global.common.response.ApiResponse;
import com.example.neo_backend.domain.post.dto.PostRequestDto;
import com.example.neo_backend.domain.post.dto.PostResponseDto;
import com.example.neo_backend.domain.post.service.PostService;
import com.example.neo_backend.domain.user.entity.User;
import com.example.neo_backend.domain.user.repository.UserRepository;
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
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.Enumeration;
import java.util.List;

@RestController
@RequestMapping("/api/v1/post")
@RequiredArgsConstructor
@Tag(name = "post", description = "제보글 관련 API")
public class PostController {

    private final PostService postService;
    private final UserRepository userRepository;

    @PostMapping
    @Operation(summary = "제보글 작성", description = "제보글 작성 API")
    @ApiErrorCodeExample(ErrorStatus.class)
    public ResponseEntity<PostResponseDto> createPost(
            @RequestPart("dto") String dtoJson,
            @RequestPart("images") List<MultipartFile> images) {

        if (images == null || images.size() != 2) {
            throw new IllegalArgumentException("이미지는 반드시 2개 업로드해야 합니다.");
        }

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
    @ApiErrorCodeExample(ErrorStatus.class)
    public ResponseEntity<PostResponseDto> getPostDetail(@PathVariable("postId") Long postId) {
        PostResponseDto response = postService.getPostDetail(postId);
        return ResponseEntity.ok(response);
    }

    @PutMapping("/{postId}/complete")
    @Operation(summary = "제보글 완료 처리", description = "게시글의 status를 완료로 변경")
    @ApiErrorCodeExample(ErrorStatus.class)
    public ResponseEntity<PostResponseDto> completePost(@PathVariable("postId") Long postId,
                                                        HttpSession session) {
        String email = null;
        Enumeration<String> attrNames = session.getAttributeNames();
        while (attrNames.hasMoreElements()) {
            String attrName = attrNames.nextElement();
            Object attr = session.getAttribute(attrName);
            if (attr instanceof User user) {
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

    @GetMapping("/category")
    @Operation(summary = "카테고리별 제보글 조회", description = "카테고리에 따른 제보글 목록을 반환")
    @ApiErrorCodeExample(ErrorStatus.class)
    public ResponseEntity<ApiResponse> getPostsByCategory(@RequestParam String category) {
        try {
            Category enumCategory = Category.valueOf(category);
            return postService.getPostsByCategory(enumCategory);
        } catch (IllegalArgumentException e) {
            throw new GeneralException(ErrorStatus.INVALID_CATEGORY);
        }
    }

    @GetMapping("/status")
    @Operation(summary = "상태별 제보글 조회", description = "status가 true/false인 제보글 목록 조회")
    @ApiErrorCodeExample(ErrorStatus.class)
    public ResponseEntity<ApiResponse> getPostsByStatus(@RequestParam boolean status) {
        return postService.getPostsByStatus(status);
    }
}
