package com.Travel_app.db.model;

import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.time.Instant;

@Data
@Entity
@Table(name = "comment")
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "comment", nullable = false)
    private Long id;

    @NotBlank(message = "Pole musi być uzupełnione")
    @Size(max=100, message = "Maksymalna długość komentarza - 100 znaków.")
    @Column(name = "body", nullable = false, length = 100)
    private String body;

    @Column(name = "publish", nullable = false)
    private Instant publish;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "post_post_id", nullable = false)
    private Post postPost;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "user_user_id", nullable = false)
    private User userUser;

}