package com.Travel_app.db.model;

import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;

@Data
@Entity
@Table(name = "tip")
public class Tip {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "tip_id", nullable = false)
    private Long id;

    @NotBlank(message = "Pole musi być uzupełnione")
    @Column(name = "title", nullable = false, length = 45)
    private String title;

    @NotBlank(message = "Pole musi być uzupełnione")
    @Column(name = "content", nullable = false, length = 100)
    private String content;

    @NotBlank(message = "Pole musi być uzupełnione")
    @Column(name = "category", length = 45)
    private String category;

}