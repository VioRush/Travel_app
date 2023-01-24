package com.Travel_app.db.repository;

import com.Travel_app.db.model.File;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface FileRepository extends JpaRepository<File, Long> {
    @Query("select i from File i where i.post.id = ?1")
    List<File> findByPost(Long id);
    @Query("select i from File i where i.user.id = ?1")
    Optional<File> findByUser(Long id);

    @Query("select i from File i where i.destination.id = ?1")
    List<File> findByDestination(Long id);

    @Query("select i from File i where i.attraction.id = ?1")
    List<File> findByAttraction(Long id);

}