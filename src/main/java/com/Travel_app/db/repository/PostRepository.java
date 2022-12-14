package com.Travel_app.db.repository;

import com.Travel_app.db.model.Post;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface PostRepository extends JpaRepository<Post, Long> {
    @Query("select p from Post p where p.postUser.id = ?1 order by p.publish DESC")
    List<Post> findAllByUser(Long id);

    @Query(value = "select * from Post p where lower(p.title) like concat('%',lower(:keyword),'%') or lower(p.body) like concat('%',lower(:keyword),'%')", nativeQuery = true)
    List<Post> findAllByKeyword(String keyword);

    @Override
    List<Post> findAll();
}