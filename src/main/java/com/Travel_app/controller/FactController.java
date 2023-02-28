package com.Travel_app.controller;

import com.Travel_app.db.model.Fact;
import com.Travel_app.service.FactService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

@Controller
@RequestMapping("admin/facts")
public class FactController {
    @Autowired
    FactService factService;

    @GetMapping("/")
    public String getAll(Model model) {
        model.addAttribute("Facts", this.factService.findAll());
        return "Fact/Facts";
    }

    @GetMapping({"/{id}"})
    public String getFact(@PathVariable Long id, Model model, HttpServletRequest request){
        model.addAttribute("fact", this.factService.getById(id));
        return "Fact/SingleFact";
    }

    @GetMapping({"/add"})
    public String addNewFact(Model model){
        model.addAttribute("fact", new Fact());
        return "Fact/AddFact";
    }

    @PostMapping("/add")
    public String addFact(@Valid @ModelAttribute("fact") Fact fact, /*Errors*/ BindingResult result, HttpServletRequest request) {
        if(result.hasErrors()){
            result.getAllErrors().forEach(el -> System.out.println(el));
            return "Fact/AddFact";
        }

        factService.addFact(fact);

        return "redirect:/admin/facts/";
    }

    @GetMapping("/edit/{id}")
    public String editInformation(@PathVariable("id") Long id, Model model) {
        model.addAttribute("fact", this.factService.getById(id));
        model.addAttribute("factId", id);
        return "Fact/EditFact";
    }

    @PostMapping("/edit/{id}")
    public String edit(@PathVariable("id") Long id, @Valid @ModelAttribute("fact") Fact fact, /*Errors*/ BindingResult result, ModelMap model) {
        if(result.hasErrors()){
            result.getAllErrors().forEach(el -> System.out.println(el));
            model.addAttribute("factId", id);
            return "Fact/EditFact";
        }
        factService.updateFact(id, fact);
        return "redirect:/admin/facts/";
    }

    @GetMapping("/delete/{id}")
    public String deleteInformation(@PathVariable("id") Long id) {
        factService.deleteFact(id);
        return "redirect:/admin/facts/";
    }
}
