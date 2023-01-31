package com.Travel_app.service;

import com.Travel_app.db.model.Destination;
import com.Travel_app.db.model.LikedDestination;
import com.Travel_app.db.model.User;
import com.Travel_app.db.repository.LikedDestinationRepository;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
@Getter
public class LikedDestinationService {

    @Autowired
    LikedDestinationRepository likedDestinationRepository;

    public void addLikedDestination(LikedDestination liked) {
        this.likedDestinationRepository.save(liked);
    }

    public List<LikedDestination> findAllByUser(User user) {
       return this.likedDestinationRepository.findAllByUser(user.getId());
    }

    public  List<LikedDestination> findAll() {
        return this.likedDestinationRepository.findAll();
    }

    public void deleteLikedDestination(Long dest_id, Long user_id) {
        this.likedDestinationRepository.deleteByIds(dest_id, user_id);
    }

    public List<LikedDestination> findTopTen() {
        return this.likedDestinationRepository.findTopTen();
    }

    public List<LikedDestination> findTopTenByContinent(String continent) {
        return this.likedDestinationRepository.findTopTenByContinent(continent);
    }

    public List<LikedDestination> findTopThree() {
        return this.likedDestinationRepository.findTopThree();
    }
}
