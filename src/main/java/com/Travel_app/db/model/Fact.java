package com.Travel_app.db.model;

import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;

@Data
@Entity
@Table(name = "fact")
public class Fact {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "fact_id", nullable = false)
    private Long id;

    @NotBlank(message = "Pole musi być uzupełnione")
    @Column(name = "title", nullable = false, length = 45)
    private String title;

    @NotBlank(message = "Pole musi być uzupełnione")
    @Column(name = "content", nullable = false, length = 600)
    private String content;

}