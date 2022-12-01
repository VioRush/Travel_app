package com.Travel_app.db.model;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "liked_attraction", indexes = {
        @Index(name = "liked_attr_idx", columnList = "attraction_id"),
        @Index(name = "liked_attr_user_idx", columnList = "user_id")
})
public class LikedAttraction {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "liked_attraction_id", nullable = false)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "attraction_id", nullable = false)
    private Attraction attraction;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

}