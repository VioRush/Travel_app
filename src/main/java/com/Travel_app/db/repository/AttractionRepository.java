package com.Travel_app.db.repository;

import com.Travel_app.db.model.Attraction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface AttractionRepository extends JpaRepository<Attraction, Long> {
    @Query("select a from Attraction a where a.destinationDestination.id = ?1")
    List<Attraction> findAllByDestination(Long id);

    @Query("select a from Attraction a where a.destinationDestination.id = ?1 and a.category = ?2")
    List<Attraction> findAllByDestinationAndCategory(Long id, String category);

    @Query(value = "select * from Attraction a where lower(a.name) like concat('%',lower(:keyword),'%')", nativeQuery = true)
    List<Attraction> findAllByKeyword(String keyword);
}