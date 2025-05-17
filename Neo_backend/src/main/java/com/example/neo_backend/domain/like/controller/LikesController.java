package com.example.neo_backend.domain.like.controller;

import com.example.neo_backend.domain.like.dto.LikeRequestDto;
import com.example.neo_backend.domain.like.dto.LikeResponseDto;
import com.example.neo_backend.domain.like.service.LikesService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/likes")
@Tag(name = "likes", description = "좋아요 관련 API")
public class LikesController {

    private final LikesService likesService;

    @PostMapping
    @Operation(summary = "좋아요 생성", description = "게시글에 좋아요 또는 좋아요 취소를 요청합니다.")
    public ResponseEntity<LikeResponseDto> likePost(@RequestBody LikeRequestDto dto) {
        LikeResponseDto response = likesService.likePost(dto);
        return ResponseEntity.ok(response);
    }


}
