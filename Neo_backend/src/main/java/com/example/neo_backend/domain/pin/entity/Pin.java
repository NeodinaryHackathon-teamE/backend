package com.example.neo_backend.domain.pin.entity;

import com.example.neo_backend.domain.image.entity.Image;
import com.example.neo_backend.domain.like.entity.Likes;
import com.example.neo_backend.domain.post.entity.Post;
import com.example.neo_backend.global.common.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class Pin extends BaseEntity {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long pinId;

    private Double latitude;  // 위도
    private Double longitude; // 경도

    @OneToMany(mappedBy = "pin")
    private List<Post> posts = new ArrayList<>();

    @OneToMany(mappedBy = "pin")
    private List<Image> imageList = new ArrayList<>();

    @OneToMany(mappedBy = "pin")
    private List<Likes> likeList = new ArrayList<>();

}
