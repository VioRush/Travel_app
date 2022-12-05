package com.Travel_app.service;

import com.Travel_app.db.model.Post;
import com.Travel_app.db.repository.PostRepository;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
@Getter
public class BlogService {
    @Autowired
    PostRepository postRepository;

    public Object findAllPosts() {
        return this.postRepository.findAll();
    }

    public Page<Post> findPostsPaginated(int pageNo, int pageSize){
        Pageable pageable = PageRequest.of(pageNo - 1, pageSize);
        return this.postRepository.findAll(pageable);
    }

    public Object getById(Long id) {
        Optional<Post> post=postRepository.findById(id);
        return post.isEmpty()? null:post.get();
    }

    public void addPost(Post post) {
        this.postRepository.save(post);
    }

    public List<Post> getPostsByUser(Long id) {
        return this.postRepository.findAllByUser(id);
    }
}
