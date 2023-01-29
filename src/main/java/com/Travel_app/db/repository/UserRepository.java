package com.Travel_app.db.repository;

import com.Travel_app.db.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface UserRepository extends JpaRepository<User, Long> {
    @Query("select u from User u where u.login = ?1")
    User findByLogin(String login);

    @Query("select u from User u where u.email = ?1")
    User findByEmail(String email);

    @Query("select u from User u where u.resetPasswordToken = ?1")
    User findByResetPasswordToken(String token);

}