package com.Travel_app.service;

import com.Travel_app.db.model.Fact;
import com.Travel_app.db.model.LikedAttraction;
import com.Travel_app.db.model.User;
import com.Travel_app.db.repository.LikedAttractionRepository;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
@Getter
public class LikedAttractionService {
    @Autowired
    LikedAttractionRepository likedAttractionRepository;

    public void addLikedAttraction(LikedAttraction liked) {
        this.likedAttractionRepository.save(liked);
    }

    public List<LikedAttraction> findAllByUser(User user) {
        return this.likedAttractionRepository.findAllByUser(user.getId());
    }

    public  List<LikedAttraction> findAll() {
        return this.likedAttractionRepository.findAll();
    }

    public void deleteLikedAttraction(Long attr_id, Long user_id) {
        this.likedAttractionRepository.deleteByIds(attr_id, user_id);
    }

    public LikedAttraction findByIds(Long id, User user) {
        Optional<LikedAttraction> attraction=likedAttractionRepository.findByIds(id, user);
        return attraction.isEmpty()? null:attraction.get();
    }
}
