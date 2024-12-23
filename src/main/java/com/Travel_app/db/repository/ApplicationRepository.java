package com.Travel_app.db.repository;

import com.Travel_app.db.model.Application;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ApplicationRepository extends JpaRepository<Application, Long> {
    @Query("select a from Application a where a.category = ?1")
    List<Application> findAllByCategory(String category);
}