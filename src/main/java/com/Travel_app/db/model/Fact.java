package com.Travel_app.db.model;

import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Data
@Entity
@Table(name = "fact")
public class Fact {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "fact_id", nullable = false)
    private Long id;

    @NotBlank(message = "Pole musi być uzupełnione")
    @Size(max=45, message = "Maksymalna długość tytułu - 45 znaków.")
    @Column(name = "title", nullable = false, length = 45)
    private String title;

    @NotBlank(message = "Pole musi być uzupełnione")
    @Size(max=45, message = "Maksymalna długość treści - 600 znaków.")
    @Column(name = "content", nullable = false, length = 600)
    private String content;

}