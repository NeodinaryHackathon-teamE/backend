package com.example.neo_backend.domain.post.controller;

import com.example.neo_backend.domain.post.dto.PostRequestDto;
import com.example.neo_backend.domain.post.dto.PostResponseDto;
import com.example.neo_backend.domain.post.service.PostService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequiredArgsConstructor
@RequestMapping("/api/v1/post")
@Tag(name = "post", description = "제보글관련 API")
public class PostController {

    private final PostService postService;

    @PostMapping
    @Operation(summary = "제보글 작성", description = "제보글 작성 API")
    public ResponseEntity<PostResponseDto> createPost(@RequestBody PostRequestDto dto) {
        PostResponseDto response = postService.createPost(dto);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{postId}")
    @Operation(summary = "제보글 상세 조회", description = "게시글 상세 정보 및 좋아요 수 조회")
    public ResponseEntity<PostResponseDto> getPostDetail(@PathVariable Long postId) {
        PostResponseDto response = postService.getPostDetail(postId);
        return ResponseEntity.ok(response);
    }

    @PutMapping("/{postId}/complete")
    @Operation(summary = "제보글 완료 처리", description = "게시글의 status를 true(완료)로 변경")
    public ResponseEntity<PostResponseDto> completePost(@PathVariable Long postId) {
        PostResponseDto response = postService.completePost(postId);
        return ResponseEntity.ok(response);
    }




}
