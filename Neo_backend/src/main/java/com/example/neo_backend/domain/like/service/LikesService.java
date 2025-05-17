package com.example.neo_backend.domain.like.service;

import com.example.neo_backend.domain.like.dto.LikeRequestDto;
import com.example.neo_backend.domain.like.dto.LikeResponseDto;
import com.example.neo_backend.domain.like.entity.Likes;
import com.example.neo_backend.domain.like.repository.LikesRepository;
import com.example.neo_backend.domain.post.entity.Post;
import com.example.neo_backend.domain.post.repository.PostRepository;
import com.example.neo_backend.global.common.exception.GeneralException;
import com.example.neo_backend.global.common.status.ErrorStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class LikesService {


}
