package com.Travel_app.db.repository;

import com.Travel_app.db.model.Tip;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TipRepository extends JpaRepository<Tip, Long> {
}