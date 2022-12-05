package com.Travel_app.db.repository;

import com.Travel_app.db.model.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface PostRepository extends JpaRepository<Post, Long> {
    @Query("select p from Post p where p.postUser.id = ?1 order by p.publish DESC")
    List<Post> findAllByUser(Long id);

}