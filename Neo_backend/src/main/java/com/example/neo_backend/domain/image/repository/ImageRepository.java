package com.example.neo_backend.domain.image.repository;

import com.example.neo_backend.domain.image.entity.Image;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ImageRepository  extends JpaRepository<Image, Long> {
    List<Image> findByPost_PostId(Long postId);
}
