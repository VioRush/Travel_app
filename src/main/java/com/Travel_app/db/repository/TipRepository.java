package com.Travel_app.db.repository;

import com.Travel_app.db.model.Tip;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface TipRepository extends JpaRepository<Tip, Long> {
    @Query(value = "select * from Tip t where lower(t.title) like concat('%',lower(:keyword),'%') or lower(t.content) like concat('%',lower(:keyword),'%')", nativeQuery = true)
    List<Tip> findAllByKeyword(String keyword);

    @Query("select t from Tip t where t.category = ?1")
    List<Tip> findAllByCategory(String category);
}