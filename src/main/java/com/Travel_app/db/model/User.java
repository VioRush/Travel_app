package com.Travel_app.db.model;

import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.util.LinkedHashSet;
import java.util.Set;

@Data
@Entity
@Table(name = "user")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id", nullable = false)
    private Long id;

    @NotBlank(message = "Pole musi być uzupełnione")
    @Column(name = "username", nullable = false, length = 50)
    private String username;

    @NotBlank(message = "Pole musi być uzupełnione")
    @Column(name = "login", nullable = false, length = 35)
    private String login;

    @NotBlank(message = "Pole musi być uzupełnione")
    @Column(name = "password", nullable = false, length = 100)
    private String password;

    @Column(name = "email", length = 50)
    private String email;

    @Column(name = "name", length = 20)
    private String name;

    @Column(name = "surname", length = 50)
    private String surname;

    @Column(name = "about", length = 500)
    private String about;

    //@OneToMany(mappedBy = "user")
    //private Set<Image> images = new LinkedHashSet<>();

    @OneToMany(mappedBy = "postUser")
    private Set<Post> posts = new LinkedHashSet<>();

   // @ManyToMany
    //@JoinTable(name = "liked_destination",
      //      joinColumns = @JoinColumn(name = "user_id"),
        //    inverseJoinColumns = @JoinColumn(name = "destinaton_id"))
    //private Set<Destination> destinations = new LinkedHashSet<>();

    @OneToMany(mappedBy = "userUser")
    private Set<Comment> comments = new LinkedHashSet<>();

    @ManyToMany
    @JoinTable(name = "liked_attraction",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "attraction_id"))
    private Set<Attraction> attractions = new LinkedHashSet<>();

    public User get() {
        return User.this;
    }
}