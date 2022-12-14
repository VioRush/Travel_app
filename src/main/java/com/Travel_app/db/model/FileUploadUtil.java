package com.Travel_app.db.model;

import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.*;

public class FileUploadUtil {

    public static void saveFile(String uploadDir, String fileName, MultipartFile multipartFile) throws IOException {
        Path uploadPath = Paths.get(uploadDir);

        if(!Files.exists(uploadPath)){
            Files.createDirectories(uploadPath);
        }
        Path filePath = uploadPath.resolve(fileName);
        if(!Files.exists(filePath)){
        try (InputStream inputStream = multipartFile.getInputStream()){
            //filePath = uploadPath.resolve(fileName);
            Files.copy(inputStream,filePath,StandardCopyOption.REPLACE_EXISTING);
        }
        catch (IOException ioe){
            throw new IOException("Could not save image file: " + fileName, ioe);
        }}

    }

    public static void deleteFile(String Dir, String path) throws IOException {
        Path uploadPath = Paths.get(Dir);
        Path filePath = uploadPath.resolve(path);
        Files.delete(filePath);
    }
}
