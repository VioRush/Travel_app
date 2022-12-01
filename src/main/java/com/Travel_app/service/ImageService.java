package com.Travel_app.service;

import com.Travel_app.db.model.Destination;
import com.Travel_app.db.model.Image;
import com.Travel_app.db.model.Tip;
import com.Travel_app.db.repository.ImageRepository;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
@Getter
public class ImageService {

    @Autowired
    ImageRepository imagies;

    public void addImage(Image image) {
        this.imagies.save(image);
    }

    public Object findByUser(Long id) {
        Optional<Image> im = imagies.findByUser(id);
        return im.isEmpty()? null:im.get();
    }

    public void deleteImage(Long id) {
        Image image= imagies.findByUser(id).get();
        if(image!=null){
            this.imagies.delete(image);
        }

    }

    public List<Image> findByDestination(Long id) {
        return this.imagies.findByDestination(id);
    }

    public List<Image> findByAttraction(Long id) {
        return this.imagies.findByAttraction(id);
    }
}
