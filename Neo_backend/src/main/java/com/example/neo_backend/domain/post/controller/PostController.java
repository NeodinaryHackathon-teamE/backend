package com.example.neo_backend.domain.post.controller;

import com.example.neo_backend.domain.post.dto.PostRequestDto;
import com.example.neo_backend.domain.post.dto.PostResponseDto;
import com.example.neo_backend.domain.post.service.PostService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

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


}
