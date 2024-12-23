package com.Travel_app.controller;

import com.Travel_app.db.model.*;
import com.Travel_app.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

@Controller
public class LikedController {

    @Autowired
    private LikedDestinationService likedDestinationService;
    @Autowired
    private LikedAttractionService likedAttractionService;
    @Autowired
    private FileService fileService;
    @Autowired
    private DestinationService destinationService;
    @Autowired
    private AttractionService attractionService;
    @Autowired
    private UserService userService;

    @GetMapping("/liked/")
    public String getAll(Model model, HttpServletRequest request) {
        User user = this.userService.findByLogin(request.getUserPrincipal().getName());
        List<LikedDestination> likedDestinations = this.likedDestinationService.findAllByUser(user);
        ArrayList<File> images = new ArrayList<File>();
        for (LikedDestination dest: likedDestinations){
            System.out.println(dest.getDestination().getId());
            ArrayList<File> im = new ArrayList<File>();
            im.addAll(fileService.findByDestination(dest.getDestination().getId()));
            //images.add(new ArrayList<Image>(imageService.findByDestination(dest.getId())).get(0));
            if(!im.isEmpty()) {
                images.add(im.listIterator().next());
            }
        }
        List<LikedAttraction> likedAttractions = this.likedAttractionService.findAllByUser(user);
        ArrayList<File> attraction_images = new ArrayList<File>();
        for (LikedAttraction attraction: likedAttractions){
            System.out.println(attraction.getAttraction().getId());
            ArrayList<File> im = new ArrayList<File>();
            im.addAll(fileService.findByAttraction(attraction.getAttraction().getId()));
            //images.add(new ArrayList<Image>(imageService.findByDestination(dest.getId())).get(0));
            if(!im.isEmpty()) {
                attraction_images.add(im.listIterator().next());
            }
        }

        model.addAttribute("likedDestinations", likedDestinations);
        model.addAttribute("images", images);
        model.addAttribute("likedAttractions", likedAttractions);
        model.addAttribute("attraction_images", attraction_images);
        return "Liked";
    }

    @GetMapping("/destinations/removeFromLiked/{country}/{id}")
    public String removeFromLiked(@PathVariable("country") String country, @PathVariable("id") Long id, HttpServletRequest request) {
        Destination dest = (Destination)this.destinationService.getById(id);
        User user = this.userService.findByLogin(request.getUserPrincipal().getName());
        likedDestinationService.deleteLikedDestination(dest.getId(), user.getId());
        System.out.println("remove!");
        return "redirect:/destinations/{country}";
    }

    @GetMapping("/attractions/addToLiked/{id}")
    public String addToLiked(@PathVariable("id") Long id, HttpServletRequest request) {
        Attraction attraction = (Attraction)this.attractionService.getById(id);
        User user = this.userService.findByLogin(request.getUserPrincipal().getName());
        LikedAttraction liked = new LikedAttraction();
        liked.setAttraction(attraction);
        liked.setUser(user);
        likedAttractionService.addLikedAttraction(liked);
        System.out.println("Dodano!");
        return "redirect:/liked/";
    }

    @GetMapping("/attractions/removeFromLiked/{id}")
    public String removeLiked(@PathVariable("id") Long id, HttpServletRequest request) {
        Attraction attraction = (Attraction)this.attractionService.getById(id);
        User user = this.userService.findByLogin(request.getUserPrincipal().getName());
        likedAttractionService.deleteLikedAttraction(attraction.getId(), user.getId());
        System.out.println("remove!");
        return "redirect:/liked/";
    }
}
