package com.Travel_app.db.repository;

import com.Travel_app.db.model.Post;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostRepository extends JpaRepository<Post, Long> {

}