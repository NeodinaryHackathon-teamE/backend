package com.example.neo_backend.domain.post.repository;

import com.example.neo_backend.domain.post.entity.Post;
import com.example.neo_backend.global.common.enums.Category;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PostRepository extends JpaRepository<Post, Long> {
    List<Post> findByCategory(Category category);
}
