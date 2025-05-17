package com.example.neo_backend.domain.post.dto;

import com.example.neo_backend.domain.image.entity.Image;
import com.example.neo_backend.domain.pin.dto.Pin;
import com.example.neo_backend.domain.user.dto.User;
import com.example.neo_backend.global.common.Category;
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
public class Post {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long postId;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "pin_id")
    private Pin pin;

    private String title;

    @Column(length = 160)
    private String content;

    private Boolean status; // false = 대기, true = 완료

    @Enumerated(EnumType.STRING)
    private Category category;

    @OneToMany(mappedBy = "post")
    private List<Image> images = new ArrayList<>();
}
