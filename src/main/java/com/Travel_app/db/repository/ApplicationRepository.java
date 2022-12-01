package com.Travel_app.db.repository;

import com.Travel_app.db.model.Application;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ApplicationRepository extends JpaRepository<Application, Long> {
}