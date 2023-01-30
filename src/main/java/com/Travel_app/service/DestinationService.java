package com.Travel_app.service;

import com.Travel_app.db.model.Destination;
import com.Travel_app.db.repository.DestinationRepository;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
@Getter
public class DestinationService {
    @Autowired
    DestinationRepository destinations;

    public List<Destination> findAll(){
        return this.destinations.findAll();
    }

    public Object getById(Long id) {
        Optional<Destination> destination=destinations.findById(id);
        return destination.isEmpty()? null:destination.get();
    }

    public void addDestination(Destination destination) {
        this.destinations.save(destination);
    }

    public void updateDestination(Long id, Destination destination) {
        Destination dest = destinations.findById(id).get();
        dest.setTown(destination.getTown());
        dest.setCountry(destination.getCountry());
        dest.setContinent(destination.getContinent());
        dest.setDescription(destination.getDescription());
        this.destinations.save(dest);
    }

    public void deleteDestination(Long id) {
        Destination destination = destinations.findById(id).get();
        this.destinations.delete(destination);
    }

    public List<Destination> findAllByCountry(String country) {
        return this.destinations.findAllByCountry(country);
    }

    public List<Destination> findDestinationsByKeyword(String keyword) {
        return this.destinations.findAllByKeyword(keyword);
    }

    public List<String> findCountriesByContinent(String continent) {
        return this.destinations.getCountriesByContinent(continent);
    }
}
