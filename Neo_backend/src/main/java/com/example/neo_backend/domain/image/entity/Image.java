package com.example.neo_backend.domain.image.entity;

import com.example.neo_backend.domain.pin.entity.Pin;
import com.example.neo_backend.domain.post.entity.Post;
import com.example.neo_backend.domain.user.entity.User;
import com.example.neo_backend.global.common.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class Image extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long imageId;

    @ManyToOne
    @JoinColumn(name = "post_id", nullable = false)
    private Post post;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "pin_id")
    private Pin pin;

    private String imageURL;
}
