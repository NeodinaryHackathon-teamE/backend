package com.example.neo_backend.domain.post.controller;

import com.example.neo_backend.global.common.enums.Category;
import com.example.neo_backend.global.common.exception.GeneralException;
import com.example.neo_backend.global.common.response.ApiResponse;
import com.example.neo_backend.domain.post.service.PostService;
import com.example.neo_backend.global.common.status.ErrorStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/post")
@RequiredArgsConstructor
public class PostController {

    private final PostService postService;

    @GetMapping("/category")
    public ResponseEntity<ApiResponse> getPostsByCategory(@RequestParam String category) {
        try {
            Category enumCategory = Category.valueOf(category);
            return postService.getPostsByCategory(enumCategory);
        } catch (IllegalArgumentException e) {
            throw new GeneralException(ErrorStatus.INVALID_CATEGORY);
        }
    }

    @GetMapping("/status")
    public ResponseEntity<ApiResponse> getPostsByStatus(@RequestParam boolean status) {
        return postService.getPostsByStatus(status);
    }


}
