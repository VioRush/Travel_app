package com.Travel_app.service;

import com.Travel_app.db.model.Comment;
import com.Travel_app.db.model.Post;
import com.Travel_app.db.repository.CommentRepository;
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
    @Autowired
    CommentRepository commentRepository;

    public Object findAllPosts() {
        return this.postRepository.findAll();
    }

    public Page<Post> findPostsPaginated(int pageNo, int pageSize){
        Pageable pageable = PageRequest.of(pageNo - 1, pageSize);
        return this.postRepository.findAll(pageable);
    }

    public List<Post> findPostsByKeyword(String keyword){
        return this.postRepository.findAllByKeyword(keyword);
    }

    public Object getPostById(Long id) {
        Optional<Post> post=postRepository.findById(id);
        return post.isEmpty()? null:post.get();
    }

    public void addPost(Post post) {
        this.postRepository.save(post);
    }

    public List<Post> getPostsByUser(Long id) {
        return this.postRepository.findAllByUser(id);
    }

    public List<Comment> getCommentsByPost(Long id) {
        return this.commentRepository.findAllByPost(id);
    }

    public void addComment(Comment comment) {
        this.commentRepository.save(comment);
    }

    public void updatePost(Long id, Post post) {
        Post p = postRepository.findById(id).get();
        p.setTitle(post.getTitle());
        p.setBody(post.getBody());
        this.postRepository.save(p);
    }

    public void deleteCommentsByPost(Long id) {
        List<Comment> comments = this.commentRepository.findAllByPost(id);
        for (Comment c: comments){
            this.commentRepository.delete(c);
        }
    }

    public void deletePost(Long id) {
        this.postRepository.delete((Post)getPostById(id));
    }
}
