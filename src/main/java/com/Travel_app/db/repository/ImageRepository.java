package com.Travel_app.db.repository;

import com.Travel_app.db.model.Image;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface ImageRepository extends JpaRepository<Image, Long> {
    @Query("select i from Image i where i.user.id = ?1")
    Optional<Image> findByUser(Long id);

    @Query("select i from Image i where i.destination.id = ?1")
    List<Image> findByDestination(Long id);

    @Query("select i from Image i where i.attraction.id = ?1")
    List<Image> findByAttraction(Long id);

}