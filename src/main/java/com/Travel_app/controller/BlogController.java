package com.Travel_app.controller;

import com.Travel_app.db.model.*;
import com.Travel_app.service.BlogService;
import com.Travel_app.service.ImageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
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
import java.util.List;

@Controller
@RequestMapping("/blog")
public class BlogController {
    @Autowired
    BlogService blogService;
    @Autowired
    ImageService imageService;
    //ArrayList<MultipartFile> addedImages = new ArrayList<>();

    @GetMapping("/")
    public String getAll(Model model) {
        return findPostsPaginated(1,model);
    }

    @GetMapping("/page/{pageNo}")
    public String findPostsPaginated(@PathVariable(value = "pageNo") int pageNo, Model model){
        int pageSize = 5;

        Page<Post> page = blogService.findPostsPaginated(pageNo,pageSize);
        List<Post> posts = page.getContent();

        model.addAttribute("currentPage", pageNo);
        model.addAttribute("totalPages", page.getTotalPages());
        model.addAttribute("totalPostNumber", page.getTotalElements());
        model.addAttribute("posts", posts);

        return "Blog/StartPage";
    }

    @GetMapping("/add")
    public String addPost(Model model){
        model.addAttribute("post", new Post());
        model.addAttribute("imagesToAdd", new ArrayList<MultipartFile>());
        return "Blog/AddPost";
    }

    @PostMapping("/add")
    public String addPost(@Valid @ModelAttribute("post") Post post,@ModelAttribute("imagesToAdd") ArrayList<MultipartFile> imagesToAdd, Model model, /*Errors*/ BindingResult result, HttpServletRequest request) throws IOException {
        if(result.hasErrors()){
            result.getAllErrors().forEach(el -> System.out.println(el));
            return "Blog/AddPost";
        }
        blogService.addPost(post);

        for(MultipartFile multipartFile: imagesToAdd){
            String filename = StringUtils.cleanPath(multipartFile.getOriginalFilename());
            if(!filename.isEmpty()){
                Image im = new Image();
                im.setDescription("Zdjęcie dołączone do postu");
                im.setPath("blog/" + filename);
                im.setPost(post);
                imageService.addImage(im);
                FileUploadUtil.saveFile("C:/Users/Asus/Desktop/Semestr 7/Dyplom/Travel_app/src/main/resources/static/blog/", filename, multipartFile);
            }
        }
        //model.addAttribute("newinformation", information);

        return "Blog/Success";
    }

    @PostMapping("/add/addImage")
    public String addImage(@ModelAttribute("imagesToAdd") ArrayList<MultipartFile> imagesToAdd,@RequestParam("image") MultipartFile multipartFile, Model model, HttpServletRequest request) throws IOException {
        imagesToAdd.add(multipartFile);
        return "Blog/AddPost";
    }
}
