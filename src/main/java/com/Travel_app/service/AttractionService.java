package com.Travel_app.service;

import com.Travel_app.db.model.Attraction;
import com.Travel_app.db.repository.AttractionRepository;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
@Getter
public class AttractionService {
    @Autowired
    AttractionRepository attractions;

    public List<Attraction> findAll(){
        return this.attractions.findAll();
    }

    public List<Attraction> findAttractionsByKeyword(String keyword){
        return this.attractions.findAllByKeyword(keyword);
    }

    public Object getById(Long id) {
        Optional<Attraction> attraction=attractions.findById(id);
        return attraction.isEmpty()? null:attraction.get();
    }

    public void addAttraction(Attraction attraction) {
        this.attractions.save(attraction);
    }

    public void updateAttraction(Long id, Attraction attraction) {
        Attraction a = attractions.findById(id).get();
        a.setName(attraction.getName());
        a.setDescription(attraction.getDescription());
        a.setPrice(attraction.getPrice());
        a.setCategory(attraction.getCategory());
        a.setContact(attraction.getContact());
        a.setDestinationDestination(attraction.getDestinationDestination());
        this.attractions.save(a);
    }

    public void deleteAttraction(Long id){
        Attraction attraction = attractions.findById(id).get();
        this.attractions.delete(attraction);
    }


    public List<Attraction> findByDestination(Long id) {
        return this.attractions.findAllByDestination(id);
    }
}
