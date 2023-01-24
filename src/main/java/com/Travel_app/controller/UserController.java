package com.Travel_app.controller;

import com.Travel_app.db.model.File;
import com.Travel_app.db.model.FileUploadUtil;
import com.Travel_app.db.model.User;
import com.Travel_app.service.FileService;
import com.Travel_app.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

@Controller
public class UserController {

    @Autowired
    UserService userService;

    @Autowired
    FileService fileService;

    public static String upload_directory = System.getProperty("user.dir");

    @GetMapping("/admin/users/")
    public String getAll(Model model) {
        model.addAttribute("Users", this.userService.findAll());
        return "User/Users";
    }

    @GetMapping("/users/user")
    public String getUser(Model model, HttpServletRequest request){
        User user = this.userService.findByLogin(request.getUserPrincipal().getName());
        System.out.println(user.getId());
        model.addAttribute("user", user);
        model.addAttribute("image", this.fileService.findByUser(user.getId()));
        return "User/SingleUser";
    }

    @GetMapping("/users/edit/{id}")
    public String editUser(@PathVariable("id") Long id, Model model) {
        model.addAttribute("user", this.userService.getById(id));
        model.addAttribute("userId", id);
        return "User/EditUser";
    }

    @PostMapping("users/save/{id}")
    public String edit(@PathVariable("id") Long id, @ModelAttribute("user") User user, @RequestParam("image") MultipartFile multipartFile, HttpServletRequest request) throws IOException {
        String filename = StringUtils.cleanPath(multipartFile.getOriginalFilename());
        if(!filename.isEmpty()){
            File im = new File();
            im.setDescription("Zdjęcie profiliowe");
            im.setPath("user-photos/" + filename);
            im.setUser(user);
            fileService.addImage(im);
            FileUploadUtil.saveFile("C:/Users/Asus/Desktop/Semestr 7/Dyplom/Travel_app/src/main/resources/static/user-photos/", filename, multipartFile);
        }

        userService.updateUser(id, user);
        return "redirect:/users/user";
    }

    @GetMapping("/admin/users/delete/{id}")
    public String deleteUser(@PathVariable("id") Long id) {
        System.out.println(id);
        fileService.deleteImage(id);
        userService.deleteUser(id);
        return "redirect:/admin/users/";
    }
}
