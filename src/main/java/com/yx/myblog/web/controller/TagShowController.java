package com.yx.myblog.web.controller;/*
    @auther
    @create ---
*/

import com.yx.myblog.po.Blog;
import com.yx.myblog.po.Tag;
import com.yx.myblog.service.BlogService;
import com.yx.myblog.service.TagService;
import com.yx.myblog.vo.BlogQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@Controller
public class TagShowController {
    @Autowired
    private TagService tagService;
    @Autowired
    private BlogService blogService;


    @GetMapping("/toTagShow/{id}")
    public String toTypeShow(@PageableDefault(size = 2, sort = {"updateTime"}, direction = Sort.Direction.DESC) Pageable pageable,
                             @PathVariable Long id, Model model){
        List<Tag> tags = tagService.listTag(500);
        if (id==-1){
            id=tags.get(0).getId();
        }
        model.addAttribute("tags", tags);
        BlogQuery blogQuery = new BlogQuery();

        Page<Blog> blogs = blogService.listBlog(pageable, id);
        model.addAttribute("page", blogs);
        model.addAttribute("activeTagId",id);
        return "tags";


    }
}
