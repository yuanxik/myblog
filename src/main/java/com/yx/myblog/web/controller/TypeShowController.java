package com.yx.myblog.web.controller;/*
    @auther
    @create ---
*/

import com.yx.myblog.po.Blog;
import com.yx.myblog.po.Type;
import com.yx.myblog.service.BlogService;
import com.yx.myblog.service.TypeService;
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
public class TypeShowController {
    @Autowired
    private TypeService typeService;
    @Autowired
    private BlogService blogService;

    @GetMapping("/toTypeShow/{id}")
    public String toTypeShow(@PageableDefault(size = 2, sort = {"updateTime"}, direction = Sort.Direction.DESC) Pageable pageable,
                             @PathVariable Long id, Model model){
        List<Type> types = typeService.listType(500);
        if (id==-1){
            id=types.get(0).getId();
        }
        model.addAttribute("types",types);
        BlogQuery blogQuery = new BlogQuery();
        blogQuery.setTypeId(id);
        Page<Blog> blogs = blogService.listBlog(pageable, blogQuery);
        model.addAttribute("page",blogs);
        model.addAttribute("activeTypeId",id);
        return "types";
    }
    @GetMapping("/toTypePageShow}")
    public String toTypeShowPage(@PageableDefault(size = 2, sort = {"updateTime"}, direction = Sort.Direction.DESC) Pageable pageable,
                                  Model model){
        List<Type> types = typeService.listType(500);

        model.addAttribute("types",types);
        BlogQuery blogQuery = new BlogQuery();

        Page<Blog> blogs = blogService.listBlog(pageable, blogQuery);
        model.addAttribute("page",blogs);

        return "types";
    }

}
