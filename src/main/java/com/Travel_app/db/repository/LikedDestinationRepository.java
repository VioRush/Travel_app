package com.Travel_app.db.repository;

import com.Travel_app.db.model.LikedDestination;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface LikedDestinationRepository extends JpaRepository<LikedDestination, Long> {
    @Query("select l from LikedDestination l where l.user.id = ?1")
    List<LikedDestination> findAllByUser(Long id);

    @Transactional
    @Modifying
    @Query("delete from LikedDestination l where l.destination.id = ?1 and l.user.id = ?2")
    void deleteByIds(Long id, Long id1);

    @Query(value = "select *, count(*) c " +
            "from Liked_Destination ld, Destination d " +
            "where ld.destination_id = d.destination_id " +
            "group by d.town " +
            "order by c desc " +
            "limit 10", nativeQuery = true)
    List<LikedDestination> findTopTen();
}