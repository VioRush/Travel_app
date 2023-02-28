package com.Travel_app.db.model;

import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Data
@Entity
@Table(name = "tip")
public class Tip {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "tip_id", nullable = false)
    private Long id;

    @NotBlank(message = "Pole musi być uzupełnione")
    @Size(max=45, message = "Maksymalna długość tytułu - 45 znaków.")
    @Column(name = "title", nullable = false, length = 45)
    private String title;

    @NotBlank(message = "Pole musi być uzupełnione")
    @Size(max=100, message = "Maksymalna długość treści - 100 znaków.")
    @Column(name = "content", nullable = false, length = 100)
    private String content;

    @NotBlank(message = "Pole musi być uzupełnione")
    @Column(name = "category", length = 45)
    private String category;

}