package com.Travel_app.db.repository;

import com.Travel_app.db.model.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Long> {
    @Query("select c from Comment c where c.postPost.id = ?1 order by c.publish DESC")
    List<Comment> findAllByPost(Long id);
}