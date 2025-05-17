package com.example.neo_backend.domain.post.repository;

import com.example.neo_backend.domain.post.entity.Post;
import com.example.neo_backend.domain.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PostRepository extends JpaRepository<Post, Long> {
    int countByUser(User user);
}
