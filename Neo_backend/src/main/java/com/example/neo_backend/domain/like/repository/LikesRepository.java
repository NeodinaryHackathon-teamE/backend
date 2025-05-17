package com.example.neo_backend.domain.like.repository;

import com.example.neo_backend.domain.like.entity.Likes;
import com.example.neo_backend.domain.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LikesRepository extends JpaRepository<Likes, Long> {
    Long countByPostPostIdAndIsLikedTrue(Long postId);
    int countByUser(User user);

}