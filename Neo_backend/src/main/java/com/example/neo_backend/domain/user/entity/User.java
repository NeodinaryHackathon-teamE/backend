package com.example.neo_backend.domain.user.entity;

import com.example.neo_backend.domain.post.entity.Post;
import com.example.neo_backend.global.common.entity.BaseEntity;
import com.example.neo_backend.domain.image.entity.Image;
import com.example.neo_backend.domain.like.entity.Likes;
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

import lombok.*;

@Entity
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class User extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userId;

    @Column(nullable = false, length = 20)
    private String nickName;

    @Column(nullable = false, length = 50, unique = true)
    private String email;

    @Column(nullable = false, length = 100)
    private String password;

}
