package com.Travel_app.db.model;

import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.LinkedHashSet;
import java.util.Set;

@Data
@Entity
@Table(name = "destination")
public class Destination {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "destination_id", nullable = false)
    private Long id;

    @NotBlank(message = "Pole musi być uzupełnione")
    @Size(max=50, message = "Maksymalna długość nazwy miejscowości - 50 znaków.")
    @Column(name = "town", nullable = false, length = 50)
    private String town;

    @NotBlank(message = "Pole musi być uzupełnione")
    @Size(max=45, message = "Maksymalna długość nazwy kraju - 45 znaków.")
    @Column(name = "country", nullable = false, length = 45)
    private String country;

    @NotBlank(message = "Pole musi być uzupełnione")
    @Column(name = "continent", length = 20)
    private String continent;

    @NotBlank(message = "Pole musi być uzupełnione")
    @Size(max=10000, message = "Maksymalna długość opisu - 10000 znaków.")
    @Column(name = "description", nullable = false, length = 10000)
    private String description;

   // @OneToMany(mappedBy = "destination")
    //private Set<Image> images = new LinkedHashSet<>();
/*
    @OneToMany(mappedBy = "destinationDestination")
    private Set<Application> applications = new LinkedHashSet<>();

    @OneToMany(mappedBy = "destinationDestination")
    private Set<Attraction> attractions = new LinkedHashSet<>();
*/
}