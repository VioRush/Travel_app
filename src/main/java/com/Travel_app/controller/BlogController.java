package com.Travel_app.controller;

import com.Travel_app.db.model.*;
import com.Travel_app.service.BlogService;
import com.Travel_app.service.FileService;
import com.Travel_app.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.io.IOException;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

@Controller
@RequestMapping("/blog")
public class BlogController {
    @Autowired
    BlogService blogService;
    @Autowired
    UserService userService;
    @Autowired
    FileService fileService;
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

    @GetMapping("/search")
    public String searchByKeyword(String keyword, Model model){
        if(keyword != null){
            List<Post> posts = blogService.findPostsByKeyword(keyword);
            model.addAttribute("posts", posts);
            return "Blog/StartPage";
        }
        else{
            return findPostsPaginated(1,model);
        }
    }

    @GetMapping("/add")
    public String addPost(Model model){
        model.addAttribute("post", new Post());
        return "Blog/AddPost";
    }

    @PostMapping("/add")
    public String addPost(@Valid @ModelAttribute("post") Post post,/*Errors*/ BindingResult result,Model model, @RequestParam("upload_images") MultipartFile[] multipartFiles, HttpServletRequest request) throws IOException {  // @ModelAttribute("imagesToAdd") ArrayList<MultipartFile> imagesToAdd,
        if(result.hasErrors()){
            result.getAllErrors().forEach(el -> System.out.println(el));
            return "Blog/AddPost";
        }
        User user = this.userService.findByLogin(request.getUserPrincipal().getName());
        post.setPostUser(user);
        post.setPublish(Instant.now());
        blogService.addPost(post);

        for(MultipartFile multipartFile: multipartFiles){
            String filename = StringUtils.cleanPath(multipartFile.getOriginalFilename());
            if(!filename.isEmpty()){
                int temp = ThreadLocalRandom.current().nextInt(0,1001);
                File file = new File();
                file.setDescription("Plik dołączony do postu");
                file.setPath("blog/" + temp + filename);
                file.setPost(post);
                fileService.addImage(file);
                FileUploadUtil.saveFile("C:/Users/Asus/Desktop/Semestr 7/Dyplom/Travel_app/src/main/resources/static/blog/", temp + filename, multipartFile);
            }
        }

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

    @GetMapping("/posts/{id}")
    public String getPost(@PathVariable Long id, Model model, HttpServletRequest request){
        List<Comment> comments = blogService.getCommentsByPost(id);
        List<File> profile_images = new ArrayList<>();
        if(request.getUserPrincipal() != null){
            User user = this.userService.findByLogin(request.getUserPrincipal().getName());
            File profile_image = (File) fileService.findByUser(user.getId());
            model.addAttribute("profile_image", profile_image);
        }

        for(Comment c: comments){
            File f = (File) fileService.findByUser(c.getUserUser().getId());
            if (f != null) profile_images.add(f);
        }
        model.addAttribute("post", blogService.getPostById(id));
        model.addAttribute("comments", comments);
        model.addAttribute("images", fileService.findByPost(id));
        model.addAttribute("profile_images", profile_images);
        model.addAttribute("new_comment", new Comment());
        return "Blog/SinglePost";
    }

    @PostMapping("/posts/{id}")
    public String addComment(@PathVariable Long id, @ModelAttribute("comment") Comment comment, HttpServletRequest request){
        if(!comment.getBody().isEmpty()){
            comment.setPostPost((Post)blogService.getPostById(id));
            User user = this.userService.findByLogin(request.getUserPrincipal().getName());
            comment.setUserUser(user);
            comment.setPublish(Instant.now());
            blogService.addComment(comment);
        }

        //getImages where user_id not null?
        return "redirect:/blog/posts/" + id;
    }

    @GetMapping("/posts/edit/{id}")
    public String editPost(@PathVariable Long id,Model model){
        model.addAttribute("post", blogService.getPostById(id));
        model.addAttribute("postId", id);
        List<File> images = fileService.findByPost(id);
        model.addAttribute("images", images);
        model.addAttribute("toDelete", new Integer[images.size()]);
        return "Blog/EditPost";
    }

    @PostMapping("/posts/save/{id}")
    public String editPost(@PathVariable Long id, @Valid @ModelAttribute("post") Post post,/*Errors*/ BindingResult result, ModelMap model, @RequestParam(value = "upload_images") MultipartFile[] multipartFiles, @RequestParam(value="toDelete",required = false) Long toDelete, HttpServletRequest request) throws IOException {  // @ModelAttribute("imagesToAdd") ArrayList<MultipartFile> imagesToAdd,
        if(result.hasErrors()){
            result.getAllErrors().forEach(el -> System.out.println(el));
            model.addAttribute("postId", id);
            List<File> images = fileService.findByPost(id);
            model.addAttribute("images", images);
            model.addAttribute("toDelete", new Integer[images.size()]);
            return "Blog/EditPost";
        }
        blogService.updatePost(id, post);
        if(toDelete != null) fileService.deleteImageById(toDelete);
        for(MultipartFile multipartFile: multipartFiles){
            String filename = StringUtils.cleanPath(multipartFile.getOriginalFilename());
            if(!filename.isEmpty()){
                int temp = ThreadLocalRandom.current().nextInt(0,1001);
                File file = new File();
                file.setDescription("Plik dołączony do postu");
                file.setPath("blog/" + temp + filename);
                file.setPost(post);
                fileService.addImage(file);
                FileUploadUtil.saveFile("C:/Users/Asus/Desktop/Semestr 7/Dyplom/Travel_app/src/main/resources/static/blog/", temp + filename, multipartFile);
            }
        }

        return "redirect:/blog/myPosts";
    }

    @GetMapping("/posts/delete/{id}")
    public String deletePost(@PathVariable Long id,Model model){
        blogService.deleteCommentsByPost(id);
        fileService.deleteImageByPost(id);
        blogService.deletePost(id);
        return "redirect:/blog/myPosts";
    }
}
