package com.example.neo_backend.domain.like.entity;

import com.example.neo_backend.domain.pin.entity.Pin;
import com.example.neo_backend.domain.post.entity.Post;
import com.example.neo_backend.domain.user.entity.User;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class Likes {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long likeId;

    @ManyToOne
    @JoinColumn(name = "post_id", nullable = false)
    private Post post;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne
    @JoinColumn(name = "pin_id", nullable = false)
    private Pin pin;

    @Column(nullable = false, columnDefinition = "BOOLEAN DEFAULT false")
    private Boolean isLiked;
}