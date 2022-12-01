package com.Travel_app.db.repository;

import com.Travel_app.db.model.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentRepository extends JpaRepository<Comment, Long> {
}