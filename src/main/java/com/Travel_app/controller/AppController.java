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
    ArrayList<String> continents = new ArrayList<String>(Arrays.asList(new String[]{"Afryka", "Ameryka Południowa", "Ameryka Północna", "Antarktyda", "Australia", "Azja", "Europa"}));

    @GetMapping("")
    public String viewHomePage(Model model, HttpServletRequest request) {
        List<Destination> destinations = this.destinationService.findAll();
        countries.clear();
        System.out.println(countries.isEmpty());
        ArrayList<Destination> dests = new ArrayList<Destination>();
        ArrayList<File> images = new ArrayList<File>();
        for (Destination dest: destinations){
            if(countries.isEmpty() || !countries.contains(dest.getCountry())){
                countries.add(dest.getCountry());
                dests.add(dest);
                ArrayList<File> im = new ArrayList<File>();
                im.addAll(fileService.findByDestination(dest.getId()));
                //images.add(new ArrayList<Image>(imageService.findByDestination(dest.getId())).get(0));
                if(!im.isEmpty()) {
                    images.add(im.listIterator().next());
                }
            }
        }
        List<LikedDestination> top = likedDestinationService.findTopTen();
        System.out.println(top.size());
        List<Fact> facts  = factService.findAll();

        System.out.println(model.containsAttribute("attractions"));

        Random rand = new Random();
        model.addAttribute("fact", facts.get(rand.nextInt(facts.size())));
        model.addAttribute("countries", dests);
        model.addAttribute("top", top);
        model.addAttribute("images", images);
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
            return viewHomePage(model, request);
        }
        else{
            return viewHomePage(model, request);
        }
    }

    @GetMapping("/destinations/{country}")
    public String getDestinations(@PathVariable String country, Model model, HttpServletRequest request){
        List<Destination> destinations = this.destinationService.findAllByCountry(country);
        List<LikedDestination> likedDestinations = this.likedDestinationService.findAllByUser(this.userService.findByLogin(request.getUserPrincipal().getName()));
        ArrayList<Destination> liked = new ArrayList<>();
        for (LikedDestination l: likedDestinations){
            liked.add(l.getDestination());
        }
        ArrayList<File> images = new ArrayList<File>();
        for (Destination dest: destinations){
            System.out.println(dest.getId() + "czy liked:" + liked.contains(dest));
            ArrayList<File> im = new ArrayList<File>();
            im.addAll(fileService.findByDestination(dest.getId()));
            //images.add(new ArrayList<Image>(imageService.findByDestination(dest.getId())).get(0));
            if(!im.isEmpty()) {
                images.add(im.listIterator().next());
            }
        }
        model.addAttribute("liked", liked);
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
        System.out.println("Dodano!");
        return "redirect:/destinations/{country}";
    }

    @GetMapping("/destinations/{country}/details/{id}")
    public String getDestination(@PathVariable("country") String country,@PathVariable("id") Long id, Model model, HttpServletRequest request){
        List<Attraction> attractions = this.attractionService.findByDestination(id);
        List<LikedAttraction> likedAttractions = this.likedAttractionService.findAllByUser(this.userService.findByLogin(request.getUserPrincipal().getName()));
        ArrayList<Attraction> liked = new ArrayList<>();
        for (LikedAttraction l: likedAttractions){
            liked.add(l.getAttraction());
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
        model.addAttribute("attractions", attractions);
        model.addAttribute("liked", liked);
        model.addAttribute("attractionsImages", attractionImages);
        return "Destination/Destination";
    }

    @GetMapping("/attractions/details/{id}")
    public String getAttraction(@PathVariable("id") Long id, Model model, HttpServletRequest request){
        LikedAttraction likedAttraction = this.likedAttractionService.findByIds(id,this.userService.findByLogin(request.getUserPrincipal().getName()));
        model.addAttribute("attraction", this.attractionService.getById(id));
        model.addAttribute("images", this.fileService.findByAttraction(id));
        model.addAttribute("liked", likedAttraction);
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
        System.out.println(category);
        List<LikedDestination> top;
        if(category!=null){
            top = likedDestinationService.findTopTenByContinent(category);
        }
        else{
            top = likedDestinationService.findTopTen();
            System.out.println(top.size());
        }
        model.addAttribute("top", top);
        model.addAttribute("categories", continents);
        return "Rankings";
    }
}

