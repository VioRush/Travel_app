package com.Travel_app.db.model;

import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.time.Instant;
import java.util.LinkedHashSet;
import java.util.Set;

@Data
@Entity
@Table(name = "post")
public class Post {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "post_id", nullable = false)
    private Long id;

    @Size(max=100, message = "Maksymalna długość tytułu - 100 liter.")
    @NotBlank(message = "Pole musi być uzupełnione")
    @Column(name = "title", nullable = false, length = 100)
    private String title;

    @Size(max=2000, message = "Maksymalna długość tytułu - 2000 liter.")
    @NotBlank(message = "Pole musi być uzupełnione")
    @Column(name = "body", nullable = false, length = 2000)
    private String body;

    @Column(name = "publish", nullable = false)
    private Instant publish;

    @Column(name = "status", length = 10)
    private String status;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "post_user_id", nullable = false)
    private User postUser;

    @OneToMany(mappedBy = "post")
    private Set<Image> images = new LinkedHashSet<>();

    @OneToMany(mappedBy = "postPost")
    private Set<Comment> comments = new LinkedHashSet<>();

}