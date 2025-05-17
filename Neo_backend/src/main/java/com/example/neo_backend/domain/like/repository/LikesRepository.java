package com.example.neo_backend.domain.like.repository;

import com.example.neo_backend.domain.like.entity.Likes;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface LikesRepository extends JpaRepository<Likes, Long> {
    Long countByPostPostIdAndIsLikedTrue(Long postId);
    boolean existsByPostPostIdAndUserUserId(Long postId, Long userId);

    void deleteByPostPostIdAndUserUserId(Long postId, Long userId);

    Optional<Likes> findByPostPostIdAndUserUserId(Long postId, Long userId);
}