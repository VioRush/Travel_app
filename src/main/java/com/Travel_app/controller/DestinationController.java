package com.Travel_app.controller;

import com.Travel_app.db.model.Attraction;
import com.Travel_app.db.model.Destination;
import com.Travel_app.db.model.FileUploadUtil;
import com.Travel_app.db.model.Image;
import com.Travel_app.service.DestinationService;
import com.Travel_app.service.ImageService;
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

@Controller
@RequestMapping("admin/destinations")
public class DestinationController {
    @Autowired
    DestinationService destinationService;

    @Autowired
    ImageService imageService;

    @GetMapping("/")
    public String getAll(Model model) {
        model.addAttribute("destinations", this.destinationService.findAll());
        return "Destination/Destinations";
    }

    @GetMapping({"/{id}"})
    public String getDestination(@PathVariable Long id, Model model, HttpServletRequest request){
        model.addAttribute("destination", this.destinationService.getById(id));
        model.addAttribute("imagies", this.imageService.findByDestination(id));
        return "Destination/SingleDestination";
    }

    @PostMapping({"/{id}"})
    public String saveImageDestination(@PathVariable Long id, Model model, @RequestParam("image") MultipartFile multipartFile, HttpServletRequest request) throws IOException {
        Destination dest = (Destination)this.destinationService.getById(id);
        String filename = StringUtils.cleanPath(multipartFile.getOriginalFilename());
        if(!filename.isEmpty()){
            Image im = new Image();
            im.setDescription(dest.getCountry());
            im.setPath(dest.getCountry() + "/" + filename);
            im.setDestination(dest);
            imageService.addImage(im);
            FileUploadUtil.saveFile("C:/Users/Asus/Desktop/Semestr 7/Travel_app/src/main/resources/static/destinations/" + dest.getCountry() + "/", filename, multipartFile);
        }
        return "redirect:/admin/destinations/" + dest.getId();
    }

    @GetMapping({"/add"})
    public String addNewDestination(Model model){
        model.addAttribute("destination", new Destination());
        return "Destination/AddDestination";
    }

    @PostMapping("/add")
    public String addDestination(@Valid @ModelAttribute("destination") Destination destination,  /*Errors*/ BindingResult result, HttpServletRequest request) {
        if(result.hasErrors()){
            result.getAllErrors().forEach(el -> System.out.println(el));
            return "Destination/AddDestination";
        }

        destinationService.addDestination(destination);
        return "redirect:/admin/destinations/";
    }

    @GetMapping("/edit/{id}")
    public String editDestination(@PathVariable("id") Long id, Model model) {
        model.addAttribute("destination", this.destinationService.getById(id));
        model.addAttribute("destinationId", id);
        return "Destination/EditDestination";
    }

    @PostMapping("/save/{id}")
    public String edit(@PathVariable("id") Long id, @ModelAttribute("destination") Destination destination) {
        destinationService.updateDestination(id, destination);
        return "redirect:/admin/destinations/";
    }

    @GetMapping("/delete/{id}")
    public String deleteInformation(@PathVariable("id") Long id) {
        destinationService.deleteDestination(id);
        return "redirect:/admin/destinations/";
    }


}
