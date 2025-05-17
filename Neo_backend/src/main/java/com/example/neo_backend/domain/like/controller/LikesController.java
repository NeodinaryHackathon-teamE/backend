package com.example.neo_backend.domain.like.controller;
import com.example.neo_backend.domain.like.dto.LikeResponseDto;
import com.example.neo_backend.domain.like.service.LikesService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/likes")
@Tag(name = "likes", description = "좋아요 관련 API")
@Slf4j
public class LikesController {

    private final LikesService likesService;

    @PostMapping("")
    @Operation(summary = "좋아요 등록", description = "제보글에 좋아요를 등록합니다.")
    public ResponseEntity<Long> createLike(@RequestParam Long postId, HttpServletRequest request) {
        log.info("좋아요 등록 요청 postId: {}", postId);
        Long like = likesService.createLike(postId, request);
        return ResponseEntity.ok(like);
    }

}