package com.Travel_app.db.repository;

import com.Travel_app.db.model.Destination;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface DestinationRepository extends JpaRepository<Destination, Long> {
    @Query("select d from Destination d where d.country = ?1")
    List<Destination> findAllByCountry(String country);

    @Query(value = "select * from Destination d where lower(d.country) like concat('%',lower(:keyword),'%') or lower(d.town) like concat('%',lower(:keyword),'%')", nativeQuery = true)
    List<Destination> findAllByKeyword(String keyword);
}