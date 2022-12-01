package com.Travel_app.controller;

import com.Travel_app.db.model.Application;
import com.Travel_app.db.model.Tip;
import com.Travel_app.service.ApplicationService;
import com.Travel_app.service.DestinationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.ArrayList;
import java.util.Arrays;

@Controller
@RequestMapping("admin/applications")
public class ApplicationController {

    @Autowired
    ApplicationService applicationService;

    @Autowired
    DestinationService destinationService;

    ArrayList<String> categories = new ArrayList<String>(Arrays.asList(new String[]{"Bilety", "Mapy", "Hotele", "Transport", "Inne"}));

    @GetMapping("/")
    public String getAll(Model model) {
        model.addAttribute("applications", this.applicationService.findAll());
        return "Application/Applications";
    }

    @GetMapping({"/{id}"})
    public String getApplication(@PathVariable Long id, Model model, HttpServletRequest request){
        model.addAttribute("appl", this.applicationService.getById(id));
        model.addAttribute("applicationId", id);
        return "Application/SingleApplication";
    }

    @GetMapping({"/add"})
    public String addNewApplication(Model model){
        model.addAttribute("appl", new Application());
        model.addAttribute("destinations", this.destinationService.findAll());
        model.addAttribute("categories", categories);
        return "Application/AddApplication";
    }

    @PostMapping("/add")
    public String addApplication(@Valid @ModelAttribute("appl") Application application, /*Errors*/ BindingResult result, HttpServletRequest request) {
        if(result.hasErrors()){
            result.getAllErrors().forEach(el -> System.out.println(el));
            return "redirect:/admin/applications/add"; //"Application/AddApplication";
        }
        applicationService.addApplication(application);

        return "redirect:/admin/applications/";
    }

    @GetMapping("/edit/{id}")
    public String editApplication(@PathVariable("id") Long id, Model model) {
        model.addAttribute("appl", this.applicationService.getById(id));
        model.addAttribute("applicationId", id);
        model.addAttribute("categories", categories);
        model.addAttribute("destinations", this.destinationService.findAll());
        return "Application/EditApplication";
    }

    @PostMapping("/save/{id}")
    public String edit(@PathVariable("id") Long id, @ModelAttribute("application") Application application) {
        applicationService.updateApplication(id, application);
        return "redirect:/admin/applications/";
    }

    @GetMapping("/delete/{id}")
    public String deleteApplication(@PathVariable("id") Long id) {
        applicationService.deleteApplication(id);
        return "redirect:/admin/applications/";
    }
}
