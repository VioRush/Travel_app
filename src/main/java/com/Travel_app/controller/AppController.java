package com.Travel_app.controller;

import com.Travel_app.db.model.*;
import com.Travel_app.db.repository.UserRepository;
import com.Travel_app.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.w3c.dom.Attr;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

@Controller
public class AppController {

    @Autowired
    private UserService userService;
    @Autowired
    private DestinationService destinationService;
    @Autowired
    private AttractionService attractionService;
    @Autowired
    private FileService fileService;
    @Autowired
    private LikedDestinationService likedDestinationService;
    @Autowired
    private LikedAttractionService likedAttractionService;
    @Autowired
    private FactService factService;
    @Autowired
    private TipService tipService;

    ArrayList<String> countries = new ArrayList<String>();
    ArrayList<String> continents = new ArrayList<String>(Arrays.asList(new String[]{"Afryka", "Ameryka Południowa", "Ameryka Północna", "Antarktyda", "Australia i Oceania", "Azja", "Bliski Wschód", "Europa"}));
    ArrayList<String> categories = new ArrayList<String>(Arrays.asList(new String[]{"Muzea i wystawy", "Atrakcje wodne", "Natura i przygoda", "Kultura i historia"}));

    @GetMapping({"","/selected/{continent}"})
    public String viewHomePage(Model model,  @PathVariable(required = false) String continent, HttpServletRequest request) {
        if(continent!=null){
            model.addAttribute("selected", continent);
            List<String> countries = destinationService.findCountriesByContinent(continent);
            System.out.println(countries.size());
            System.out.println("strany: " + countries.toString());
            model.addAttribute("countries", countries);
        }

        List<LikedDestination> top = likedDestinationService.findTopThree();
        System.out.println(top.size());
        List<Fact> facts  = factService.findAll();

        Random rand = new Random();
        model.addAttribute("fact", facts.get(rand.nextInt(facts.size())));
        model.addAttribute("continents", continents);
        model.addAttribute("top", top);
        return "Main";
    }

    @GetMapping("/search")
    public String searchByKeyword(String keyword, Model model, HttpServletRequest request){
        if(keyword != null){
            List<Destination> destinations = destinationService.findDestinationsByKeyword(keyword);
            List<Attraction> attractions = attractionService.findAttractionsByKeyword(keyword);
            List<Tip> tips = tipService.findTipsByKeyword(keyword);

            System.out.println(attractions.size());
            model.addAttribute("destinations", destinations);
            model.addAttribute("attractions", attractions);
            model.addAttribute("tips", tips);
            return viewHomePage(model, null, request);
        }
        else{
            return viewHomePage(model, null, request);
        }
    }

    @GetMapping("/destinations/{country}")
    public String getDestinations(@PathVariable String country, Model model, HttpServletRequest request){
        List<Destination> destinations = this.destinationService.findAllByCountry(country);
        if(request.getUserPrincipal() != null){
            List<LikedDestination> likedDestinations = this.likedDestinationService.findAllByUser(this.userService.findByLogin(request.getUserPrincipal().getName()));
            ArrayList<Destination> liked = new ArrayList<>();
            for (LikedDestination l: likedDestinations){
                liked.add(l.getDestination());
            }
            model.addAttribute("liked", liked);
        }

        ArrayList<File> images = new ArrayList<File>();
        for (Destination dest: destinations){
            ArrayList<File> im = new ArrayList<File>();
            im.addAll(fileService.findByDestination(dest.getId()));
            //images.add(new ArrayList<Image>(imageService.findByDestination(dest.getId())).get(0));
            if(!im.isEmpty()) {
                images.add(im.listIterator().next());
            }
        }
        model.addAttribute("country", country);
        model.addAttribute("destinations", destinations);
        model.addAttribute("images", images);
        return "Destination/AllDestinations";
    }

    @GetMapping("/destinations/addToLiked/{country}/{id}")
    public String addToLiked(@PathVariable("country") String country,@PathVariable("id") Long id, HttpServletRequest request) {
        Destination dest = (Destination)this.destinationService.getById(id);
        User user = this.userService.findByLogin(request.getUserPrincipal().getName());
        LikedDestination liked = new LikedDestination();
        liked.setDestination(dest);
        liked.setUser(user);
        likedDestinationService.addLikedDestination(liked);
        return "redirect:/destinations/{country}";
    }

    @GetMapping({"/destinations/{country}/details/{id}","/destinations/{country}/details/{id}/{category}"})
    public String getDestination(@PathVariable("country") String country,@PathVariable("id") Long id, @PathVariable(value = "category", required = false) String category, Model model, HttpServletRequest request){
        List<Attraction> attractions;
        if(category !=null){
             attractions = this.attractionService.findByDestinationAndCategory(id, category);
             model.addAttribute("selectedCategory", category);
        }
        else{
            attractions = this.attractionService.findByDestination(id);
        }
        if(request.getUserPrincipal() != null) {
            List<LikedAttraction> likedAttractions = this.likedAttractionService.findAllByUser(this.userService.findByLogin(request.getUserPrincipal().getName()));
            ArrayList<Attraction> liked = new ArrayList<>();
            for (LikedAttraction l : likedAttractions) {
                liked.add(l.getAttraction());
            }
            model.addAttribute("liked", liked);
        }
        ArrayList<File> attractionImages = new ArrayList<File>();
        for (Attraction attraction: attractions){
            ArrayList<File> im = new ArrayList<File>();
            im.addAll(fileService.findByAttraction(attraction.getId()));
            //images.add(new ArrayList<Image>(imageService.findByDestination(dest.getId())).get(0));
            if(!im.isEmpty()) {
                attractionImages.add(im.listIterator().next());
            }
        }
        model.addAttribute("destination", this.destinationService.getById(id));
        model.addAttribute("images", this.fileService.findByDestination(id));
        model.addAttribute("categories", categories);
        model.addAttribute("attractions", attractions);
        model.addAttribute("attractionsImages", attractionImages);
        return "Destination/Destination";
    }

    @GetMapping("/attractions/details/{id}")
    public String getAttraction(@PathVariable("id") Long id, Model model, HttpServletRequest request){
        if(request.getUserPrincipal() != null) {
            LikedAttraction likedAttraction = this.likedAttractionService.findByIds(id,this.userService.findByLogin(request.getUserPrincipal().getName()));
            model.addAttribute("liked", likedAttraction);
        }
        model.addAttribute("attraction", this.attractionService.getById(id));
        model.addAttribute("images", this.fileService.findByAttraction(id));
        return "Attraction/Attraction";
    }

    @GetMapping("/login")
    public String login(){
        return "Login";
    }

    @GetMapping("/register")
    public String showRegistrationForm(Model model) {
        model.addAttribute("user", new User());
        return "SignUp";
    }

    @PostMapping("/post_register")
    public String processRegister(@Valid @ModelAttribute("user") User user, /*Errors*/ BindingResult result) {
        if(result.hasErrors()){
            result.getAllErrors().forEach(el -> System.out.println(el));
            return "SignUp";
        }
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        String encodedPassword = passwordEncoder.encode(user.getPassword());
        user.setPassword(encodedPassword);
        userService.save(user);
        return "redirect:/";
    }

    @GetMapping({"rankings/","rankings/{category}"})
    public String getRankings(Model model, @PathVariable(required = false) String category){
        List<LikedDestination> top;
        if(category!=null) {
            top = likedDestinationService.findTopTenByContinent(category);
            model.addAttribute("selected", category);
        }
        else {
            top = likedDestinationService.findTopTen();
        }

        ArrayList<File> images = new ArrayList<File>();
        for (LikedDestination dest: top){
            ArrayList<File> im = new ArrayList<File>();
            im.addAll(fileService.findByDestination(dest.getDestination().getId()));
            if(!im.isEmpty()) {
                images.add(im.listIterator().next());
            }
        }
        System.out.println("Fotok:       " + images.size());
        model.addAttribute("top", top);
        model.addAttribute("images", images);
        model.addAttribute("categories", continents);
        return "Rankings";
    }
}

