package com.Travel_app.db.repository;

import com.Travel_app.db.model.LikedAttraction;
import com.Travel_app.db.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

public interface LikedAttractionRepository extends JpaRepository<LikedAttraction, Long> {
    @Query("select l from LikedAttraction l where l.user.id = ?1")
    List<LikedAttraction> findAllByUser(Long id);

    @Transactional
    @Modifying
    @Query("delete from LikedAttraction l where l.attraction.id = ?1 and l.user.id = ?2")
    void deleteByIds(Long id, Long id1);

    @Query("select l from LikedAttraction l where l.attraction.id = ?1 and l.user = ?2")
    Optional<LikedAttraction> findByIds(Long id, User id1);
}