package com.Travel_app.controller;

import com.Travel_app.db.model.Attraction;
import com.Travel_app.db.model.File;
import com.Travel_app.db.model.FileUploadUtil;
import com.Travel_app.service.AttractionService;
import com.Travel_app.service.DestinationService;
import com.Travel_app.service.FileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.concurrent.ThreadLocalRandom;

@Controller
@RequestMapping("admin/attractions")
public class AttractionController {

    @Autowired
    AttractionService attractionService;
    @Autowired
    DestinationService destinationService;
    @Autowired
    FileService fileService;

    ArrayList<String> categories = new ArrayList<String>(Arrays.asList(new String[]{"Muzea i wystawy", "Atrakcje wodne", "Natura i przygoda", "Kultura i historia"}));

    @GetMapping("/")
    public String getAll(Model model) {
        model.addAttribute("AttractionList", this.attractionService.findAll());
        return "Attraction/Attractions";
    }

    @GetMapping({"/{id}"})
    public String getAttraction(@PathVariable Long id, Model model, HttpServletRequest request){
        model.addAttribute("attraction", this.attractionService.getById(id));
        model.addAttribute("imagies", this.fileService.findByAttraction(id));
        return "Attraction/SingleAttraction";
    }

    @PostMapping({"/{id}"})
    public String saveImageAttraction(@PathVariable Long id, Model model, @RequestParam("image") MultipartFile multipartFile, HttpServletRequest request) throws IOException {
        Attraction attraction = (Attraction)this.attractionService.getById(id);
        String filename = StringUtils.cleanPath(multipartFile.getOriginalFilename());
        if(!filename.isEmpty()){
            File im = new File();
            im.setDescription(attraction.getName());
            im.setPath(attraction.getName() + "/" + filename);
            im.setAttraction(attraction);
            fileService.addImage(im);
            FileUploadUtil.saveFile("C:/Users/Asus/Desktop/Semestr 7/Dyplom/Travel_app/src/main/resources/static/attractions/" + attraction.getName() + "/", filename, multipartFile);
        }
        return "redirect:/admin/attractions/" + attraction.getId();
    }

    @GetMapping({"/add"})
    public String addNewAttraction(Model model){
        model.addAttribute("attraction", new Attraction());
        model.addAttribute("destinations", this.destinationService.findAll());
        model.addAttribute("categories", categories);
        return "Attraction/AddAttraction";
    }

    @PostMapping("/add")
    public String addAttraction(@Valid @ModelAttribute("attraction") Attraction attraction, /*Errors*/ BindingResult result,  @RequestParam("upload_images") MultipartFile[] multipartFiles, HttpServletRequest request) throws IOException {
        if(result.hasErrors()){
            result.getAllErrors().forEach(el -> System.out.println(el));
            return "Attraction/AddAttraction";
        }
        attraction.setReview(0);
        attractionService.addAttraction(attraction);

        for(MultipartFile multipartFile: multipartFiles){
            String filename = StringUtils.cleanPath(multipartFile.getOriginalFilename());
            if(!filename.isEmpty()){
                int temp = ThreadLocalRandom.current().nextInt(0,1001);
                File im = new File();
                im.setDescription(attraction.getName());
                im.setPath(attraction.getName() + "/" + temp + filename);
                im.setAttraction(attraction);
                fileService.addImage(im);
                FileUploadUtil.saveFile("C:/Users/Asus/Desktop/Semestr 7/Dyplom/Travel_app/src/main/resources/static/attractions/" + attraction.getName() + "/", temp + filename, multipartFile);
            }
        }

        return "redirect:/admin/attractions/";
    }

    @GetMapping("/edit/{id}")
    public String editAttraction(@PathVariable Long id, Model model) {
        model.addAttribute("attraction", this.attractionService.getById(id));
        model.addAttribute("destinations", this.destinationService.findAll());
        model.addAttribute("categories", categories);
        model.addAttribute("attractionId", id);
        return "Attraction/EditAttraction";
    }

    @PostMapping("/save/{id}")
    public String edit(@PathVariable("id") Long id, @ModelAttribute("attraction") Attraction attraction) {
        attractionService.updateAttraction(id, attraction);
        return "redirect:/admin/attractions/";
    }

    @GetMapping("/delete/{id}")
    public String deleteInformation(@PathVariable("id") Long id) {
        attractionService.deleteAttraction(id);
        return "redirect:/admin/attractions/";
    }
}
