package com.Travel_app.db.repository;

import com.Travel_app.db.model.Fact;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FactRepository extends JpaRepository<Fact, Long> {
}