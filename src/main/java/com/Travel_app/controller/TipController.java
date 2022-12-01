package com.Travel_app.controller;

import com.Travel_app.db.model.Destination;
import com.Travel_app.db.model.Tip;
import com.Travel_app.service.TipService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Controller
@RequestMapping("admin/tips")
public class TipController {
    @Autowired
    TipService tipService;

    ArrayList<String> categories = new ArrayList<String>(Arrays.asList(new String[]{"Przed podróża", "Podczas podróży"}));

    @GetMapping("/")
    public String getAll(Model model) {
        model.addAttribute("Tips", this.tipService.findAll());
        return "Tip/Tips";
    }

    @GetMapping({"/{id}"})
    public String getTip(@PathVariable Long id, Model model, HttpServletRequest request){
        model.addAttribute("tip", this.tipService.getById(id));
        return "Tip/SingleTip";
    }

    @GetMapping({"/add"})
    public String addNewTip(Model model){
        model.addAttribute("tip", new Tip());
        model.addAttribute("categories", categories);
        return "Tip/AddTip";
    }

    @PostMapping("/add")
    public String addInformation(@Valid @ModelAttribute("tip") Tip tip, /*Errors*/ BindingResult result, HttpServletRequest request) {
        if(result.hasErrors()){
            result.getAllErrors().forEach(el -> System.out.println(el));
            return "Tip/AddTip";
        }

        tipService.addTip(tip);
        //model.addAttribute("newinformation", information);

        return "redirect:/admin/tips/";
    }

    @GetMapping("/edit/{id}")
    public String editInformation(@PathVariable("id") Long id, Model model) {
        model.addAttribute("tip", this.tipService.getById(id));
        model.addAttribute("tipId", id);
        model.addAttribute("categories", categories);
        return "Tip/EditTip";
    }

    @PostMapping("/save/{id}")
    public String edit(@PathVariable("id") Long id, @ModelAttribute("tip") Tip tip) {
        tipService.updateTip(id, tip);
        return "redirect:/admin/tips/";
    }

    @GetMapping("/delete/{id}")
    public String deleteTip(@PathVariable("id") Long id) {
        tipService.deleteTip(id);
        return "redirect:/admin/tips/";
    }
}