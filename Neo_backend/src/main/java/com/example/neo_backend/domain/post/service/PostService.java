package com.example.neo_backend.domain.post.service;

import com.example.neo_backend.domain.post.repository.PostRepository;
import com.example.neo_backend.global.common.enums.Category;
import com.example.neo_backend.global.common.exception.GeneralException;
import com.example.neo_backend.global.common.response.ApiResponse;
import com.example.neo_backend.global.common.status.SuccessStatus;
import com.example.neo_backend.global.common.status.ErrorStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PostService {

    private final PostRepository postRepository;

    public ResponseEntity<ApiResponse> getPostsByCategory(Category category) {
        var postList = postRepository.findByCategory(category);
        if (postList.isEmpty()) {
            return ApiResponse.onFailure(ErrorStatus.POST_NOT_FOUND);
        }
        return ApiResponse.onSuccess(SuccessStatus._OK, postList);
    }
}
