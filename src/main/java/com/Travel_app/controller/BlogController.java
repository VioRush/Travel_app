package com.Travel_app.controller;

import com.Travel_app.db.model.*;
import com.Travel_app.service.BlogService;
import com.Travel_app.service.ImageService;
import com.Travel_app.service.UserService;
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
    UserService userService;
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
    public String addPost(@Valid @ModelAttribute("post") Post post,/*Errors*/ BindingResult result,Model model, @RequestParam("upload_images") MultipartFile[] multipartFiles,HttpServletRequest request) throws IOException {  // @ModelAttribute("imagesToAdd") ArrayList<MultipartFile> imagesToAdd,
        if(result.hasErrors()){
            result.getAllErrors().forEach(el -> System.out.println(el));
            return "Blog/AddPost";
        }
        System.out.println(multipartFiles.length);
        //blogService.addPost(post);

        /*for(MultipartFile multipartFile: multipartFile){
            String filename = StringUtils.cleanPath(multipartFile.getOriginalFilename());
            if(!filename.isEmpty()){
                Image im = new Image();
                im.setDescription("Zdjęcie dołączone do postu");
                im.setPath("blog/" + filename);
                im.setPost(post);
                imageService.addImage(im);
                FileUploadUtil.saveFile("C:/Users/Asus/Desktop/Semestr 7/Dyplom/Travel_app/src/main/resources/static/blog/", filename, multipartFile);
            }
        }*/
        //model.addAttribute("newinformation", information);

        return "redirect:/blog/myPosts".concat("?message=success");// "Blog/MyPosts";
    }

    @GetMapping("/myPosts")
    public String myPosts(@RequestParam(value="message", required = false) String message, Model model, HttpServletRequest request){
        List<Post> posts = new ArrayList<>();
        User user = this.userService.findByLogin(request.getUserPrincipal().getName());
        posts = blogService.getPostsByUser(user.getId());
        model.addAttribute("posts", posts);
        model.addAttribute("message", message);
        return "Blog/MyPosts";
    }


    /*
    @RequestMapping(value="/addImage")
    public String addImage(@RequestParam("image") MultipartFile multipartFile, HttpServletRequest request) throws IOException {   //@ModelAttribute("imagesToAdd") ArrayList<MultipartFile> imagesToAdd,
        //imagesToAdd.add(multipartFile);
        System.out.println("Poluczilos");
        return "Blog/AddPost";
    }

    @RequestMapping("/add/addImage")
    public String addImage(@RequestParam("image") MultipartFile multipartFile) throws IOException {   //@ModelAttribute("imagesToAdd") ArrayList<MultipartFile> imagesToAdd,
        //imagesToAdd.add(multipartFile);
        System.out.println("Poluczilos");
        return "Blog/AddPost";
    }*/
}
