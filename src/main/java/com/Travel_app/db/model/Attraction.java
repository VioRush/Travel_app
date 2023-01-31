package com.Travel_app.db.model;

import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.LinkedHashSet;
import java.util.Set;

@Data
@Entity
@Table(name = "attraction")
public class Attraction {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "attraction_id", nullable = false)
    private Long id;

    @Size(max=100, message = "Maksymalna długość nazwy atrakcji - 100 liter.")
    @NotBlank(message = "Pole musi być uzupełnione")
    @Column(name = "name", nullable = false, length = 100)
    private String name;

    @Size(max=10000, message = "Długość opisu aplikacji max 10000 liter.")
    @NotBlank(message = "Pole musi być uzupełnione")
    @Column(name = "description", nullable = false, length = 10000)
    private String description;

    @NotBlank(message = "Pole musi być uzupełnione")
    @Column(name = "price", nullable = false)
    private String price;

    @Column(name = "review", nullable = false)
    private Integer review;

    @NotBlank(message = "Pole musi być uzupełnione")
    @Column(name = "category", nullable = false, length = 30)
    private String category;

    @Column(name = "contact", length = 100)
    private String contact;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "destination_destination_id", nullable = false)
    private Destination destinationDestination;

    /*
    @OneToMany(mappedBy = "attraction")
    private Set<File> images = new LinkedHashSet<>();


    @ManyToMany
    @JoinTable(name = "liked_attraction",
            joinColumns = @JoinColumn(name = "attraction_id"),
            inverseJoinColumns = @JoinColumn(name = "user_id"))
    private Set<User> users = new LinkedHashSet<>();
*/
}