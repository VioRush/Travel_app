package com.Travel_app.db.model;

import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
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
    @Size(max=50, message = "Maksymalna długość nazwy użytkownika - 50 znaków.")
    @Column(name = "username", nullable = false, length = 50)
    private String username;

    @NotBlank(message = "Pole musi być uzupełnione")
    @Size(max=35, message = "Maksymalna długość logina - 35 znaków.")
    @Column(name = "login", nullable = false, length = 35)
    private String login;

    @NotBlank(message = "Pole musi być uzupełnione")
    @Size(max=100, message = "Maksymalna długość hasła - 100 znaków.")
    @Column(name = "password", nullable = false, length = 100)
    private String password;

    @Column(name = "email", length = 50)
    @Size(max=50, message = "Maksymalna długość adresu email - 50 znaków.")
    private String email;

    @Column(name = "name", length = 20)
    @Size(max=20, message = "Maksymalna długość imienia - 20 znaków.")
    private String name;

    @Column(name = "surname", length = 50)
    @Size(max=50, message = "Maksymalna długość nazwiska - 50 znaków.")
    private String surname;

    @Column(name = "about", length = 500)
    @Size(max=500, message = "Maksymalna długość tekstu 500 znaków.")
    private String about;

    @Column(name = "reset_password_token", length = 30)
    private String resetPasswordToken;

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