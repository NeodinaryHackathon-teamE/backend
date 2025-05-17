package com.example.neo_backend.domain.post.service;

import com.example.neo_backend.domain.post.dto.PostResponseDto;
import com.example.neo_backend.domain.post.repository.PostRepository;
import com.example.neo_backend.global.common.enums.Category;
import com.example.neo_backend.global.common.response.ApiResponse;
import com.example.neo_backend.global.common.status.SuccessStatus;
import com.example.neo_backend.global.common.status.ErrorStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PostService {

    private final PostRepository postRepository;

    public ResponseEntity<ApiResponse> getPostsByCategory(Category category) {
        var postList = postRepository.findByCategory(category);
        if (postList.isEmpty()) {
            return ApiResponse.onFailure(ErrorStatus._NOT_FOUND);
        }

        List<PostResponseDto> responseList = postList.stream()
                .map(PostResponseDto::from)
                .collect(Collectors.toList());

        return ApiResponse.onSuccess(SuccessStatus._OK, responseList);
    }

    public ResponseEntity<ApiResponse> getPostsByStatus(boolean status) {
        var postList = postRepository.findByStatus((status));
        if (postList.isEmpty()) {
            return ApiResponse.onFailure(ErrorStatus._NOT_FOUND);
        }

        List<PostResponseDto> responseList = postList.stream()
                .map(PostResponseDto::from)
                .collect(Collectors.toList());

        return ApiResponse.onSuccess(SuccessStatus._OK, responseList);
    }

}