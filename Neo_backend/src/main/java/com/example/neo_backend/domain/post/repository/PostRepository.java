package com.example.neo_backend.domain.post.repository;

import com.example.neo_backend.domain.post.entity.Post;
import com.example.neo_backend.domain.user.entity.User;
import com.example.neo_backend.global.common.enums.Category;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PostRepository extends JpaRepository<Post, Long> {

    int countByUser(User user);

    List<Post> findByCategory(Category category);

    List<Post> findByStatus(boolean status);
}
