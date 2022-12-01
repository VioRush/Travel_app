package com.Travel_app.db.model;

import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
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
    @Column(name = "town", nullable = false, length = 50)
    private String town;

    @NotBlank(message = "Pole musi być uzupełnione")
    @Column(name = "country", nullable = false, length = 45)
    private String country;

    @NotBlank(message = "Pole musi być uzupełnione")
    @Column(name = "continent", length = 20)
    private String continent;

    @NotBlank(message = "Pole musi być uzupełnione")
    @Column(name = "description", nullable = false, length = 10000)
    private String description;

   // @OneToMany(mappedBy = "destination")
    //private Set<Image> images = new LinkedHashSet<>();

    @OneToMany(mappedBy = "destinationDestination")
    private Set<Application> applications = new LinkedHashSet<>();

    @OneToMany(mappedBy = "destinationDestination")
    private Set<Attraction> attractions = new LinkedHashSet<>();

}