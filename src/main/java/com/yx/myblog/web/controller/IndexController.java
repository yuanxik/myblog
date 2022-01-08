package com.yx.myblog.web.controller;/*
    @auther
    @create ---
*/

import com.yx.myblog.service.BlogService;
import com.yx.myblog.service.TagService;
import com.yx.myblog.service.TypeService;
import com.yx.myblog.vo.BlogQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class IndexController {

    @Autowired
    private BlogService blogService;
    @Autowired
    private TypeService typeService;

    @Autowired
    private TagService tagService;

    @GetMapping("/")
   public String toIndex(@PageableDefault(size = 5, sort = {"updateTime"}, direction = Sort.Direction.DESC) Pageable pageable,
                         Model model){

        //
        model.addAttribute("page",blogService.listBlog(pageable));
        //分类展示
        model.addAttribute("types",typeService.listType(6));
        //标签展示
        model.addAttribute("tags",tagService.listTag(10));
        //最新推荐
        model.addAttribute("recommendBlogs",blogService.listBlog(5));
        return "index";

   }
   @PostMapping("/toSearch")
   public String toSearch(@PageableDefault(size = 1, sort = {"updateTime"}, direction = Sort.Direction.DESC) Pageable pageable,
                          Model model,@RequestParam String query){

        model.addAttribute("page",blogService.list(pageable,"%"+query+"%"));
        model.addAttribute("query",query);
        return "search";
   }

   @GetMapping("/toView/{id}")
   public String toView(@PathVariable Long id, Model model){
        model.addAttribute("blog",blogService.getAndConvert(id));
        return "blog";
   }

}
