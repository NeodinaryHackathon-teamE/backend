package com.example.neo_backend.domain.user.controller;

import com.example.neo_backend.domain.post.dto.PostResponseDto;
import com.example.neo_backend.domain.user.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/user")
public class UserApiController {

    private final UserService userService;

    @GetMapping("/likes")
    @Operation(summary = "좋아요 누른 제보글 조회", description = "내가 좋아요 누른 제보글 목록을 조회합니다.")
    public ResponseEntity<List<PostResponseDto>> getLikedPost(HttpServletRequest request){
        List<PostResponseDto> likedPost = userService.getLikedPost(request);
        return ResponseEntity.ok(likedPost);
    }

}
