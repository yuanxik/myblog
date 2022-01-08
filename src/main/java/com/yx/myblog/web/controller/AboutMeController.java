package com.yx.myblog.web.controller;/*
    @auther
    @create ---
*/

import com.yx.myblog.po.Blog;
import com.yx.myblog.service.BlogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class AboutMeController {
    @Autowired
    private BlogService blogService;

    @GetMapping("/toAbout")
    public String toAbout(){
        return "aboutme";
    }


    @GetMapping("/getNewBlogList")
    public String getNewBlogList(Model model){
        List<Blog> blogs = blogService.listBlog(3);
        model.addAttribute("blogs",blogs);
        return "_fragment::newBlogList";
    }
}
