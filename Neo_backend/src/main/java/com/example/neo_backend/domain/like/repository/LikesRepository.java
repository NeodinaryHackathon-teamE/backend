package com.example.neo_backend.domain.like.repository;

import com.example.neo_backend.domain.like.entity.Likes;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface LikesRepository extends JpaRepository<Likes, Long> {
    Long countByPostPostIdAndIsLikedTrue(Long postId);

    @Query("SELECT l.post.postId FROM Likes l WHERE l.user.userId = :userId AND l.isLiked = true")
    List<Long> findLikedPostIdsByUserId(@Param("userId") Long userId);

    Optional<Likes> findByPostPostIdAndUserUserId(Long postId, Long userId);
}