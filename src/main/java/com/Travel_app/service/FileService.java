package com.Travel_app.service;

import com.Travel_app.db.model.File;
import com.Travel_app.db.model.FileUploadUtil;
import com.Travel_app.db.repository.FileRepository;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
@Getter
public class FileService {

    @Autowired
    FileRepository files;

    public void addImage(File file) {
        this.files.save(file);
    }

    public Object findByUser(Long id) {
        Optional<File> im = files.findByUser(id);
        return im.isEmpty()? null:im.get();
    }

    public void deleteImage(Long id) {
        File file= files.findByUser(id).get();
        if(file!=null){
            this.files.delete(file);
        }
    }

    public void deleteImageById(Long id){
        File file= files.getById(id);
        if(file!=null){
            try{
                FileUploadUtil.deleteFile("C:/Users/Asus/Desktop/Semestr 7/Dyplom/Travel_app/src/main/resources/static/", file.getPath());
            } catch (IOException e) {
                System.out.println("Nieudane usuwanie pliku");
            }
            this.files.delete(file);
        }
    }

    public List<File> findByDestination(Long id) {
        return this.files.findByDestination(id);
    }

    public List<File> findByAttraction(Long id) {
        return this.files.findByAttraction(id);
    }

    public List<File> findByPost(Long id) {
        return this.files.findByPost(id);
    }

    public void deleteImageByPost(Long id) {
        for(File im: findByPost(id)){
            deleteImageById(im.getId());
        }
    }
}
