package com.Travel_app.db.model;

import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Data
@Entity
@Table(name = "application")
public class Application {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "application_id", nullable = false)
    private Long id;

    @Size(max=30, message = "Maksymalna długość nazwy aplikacji - 30 liter.")
    @NotBlank(message = "Pole musi być uzupełnione")
    @Column(name = "app_name", nullable = false, length = 30)
    private String appName;

    @Size(max=500, message = "Opis aplikacji powinien być mniejszy od 500 liter.")
    @NotBlank(message = "Pole musi być uzupełnione")
    @Column(name = "description", nullable = false, length = 500)
    private String description;

    @Column(name = "category", nullable = false, length = 30)
    private String category;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "Destination_destination_id")
    private Destination destinationDestination;

}