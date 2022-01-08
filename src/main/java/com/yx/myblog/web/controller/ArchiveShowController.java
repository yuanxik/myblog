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
import java.util.Map;

@Controller
public class ArchiveShowController {

    @Autowired
    private BlogService blogService;

    @GetMapping("/toArchive")
    public String toArchive(Model model){
        Map<String, List<Blog>> map = blogService.archiveBlog();
        model.addAttribute("map",map);
        Long countBlog = blogService.countBlog();
        model.addAttribute("countBlog",countBlog);
        return "archives";
    }
}
