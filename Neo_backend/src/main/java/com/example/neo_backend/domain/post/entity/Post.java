package com.example.neo_backend.domain.post.entity;

import com.example.neo_backend.domain.image.entity.Image;
import com.example.neo_backend.domain.like.entity.Likes;
import com.example.neo_backend.domain.pin.entity.Pin;
import com.example.neo_backend.domain.user.entity.User;
import com.example.neo_backend.global.common.entity.BaseEntity;
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
public class Post extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long postId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
    @JoinColumn(name = "pin_id")
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

    @OneToMany(mappedBy = "post", cascade = CascadeType.ALL)
    private List<Image> imageList = new ArrayList<>();

    @OneToMany(mappedBy = "post", cascade = CascadeType.ALL)
    private List<Likes> likeList = new ArrayList<>();

    public void complete() {
        this.status = true;
    }
}
