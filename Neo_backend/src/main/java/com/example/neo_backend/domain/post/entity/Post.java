package com.example.neo_backend.domain.post.entity;

import com.example.neo_backend.domain.image.entity.Image;
import com.example.neo_backend.domain.like.entity.Likes;
import com.example.neo_backend.domain.pin.entity.Pin;
import com.example.neo_backend.domain.user.entity.User;
import com.example.neo_backend.global.common.enums.Category;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class Post {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long postId;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne
    @JoinColumn(name = "pin_id", nullable = false)
    private Pin pin;

    @Column(length = 20, nullable = false)
    private String title;

    @Column(length = 160)
    private String content;

    @Column(length = 200)
    private String place;

    @Column(nullable = false, columnDefinition = "BOOLEAN DEFAULT false")
    private Boolean status; // false = 대기, true = 완료

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Category category;


    @OneToMany(mappedBy = "post", cascade = CascadeType.ALL, orphanRemoval = true)
    @Builder.Default
    private List<Image> imageList = new ArrayList<>();

    @OneToMany(mappedBy = "post", cascade = CascadeType.ALL, orphanRemoval = true)
    @Builder.Default
    private List<Likes> likeList = new ArrayList<>();
}
