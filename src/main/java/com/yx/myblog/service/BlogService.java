package com.yx.myblog.service;/*
    @auther
    @create ---
*/

import com.yx.myblog.po.Blog;
import com.yx.myblog.vo.BlogQuery;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

import java.util.List;
import java.util.Map;

public interface BlogService {

    Blog getBlog(Long id);

    Page<Blog> listBlog(Pageable pageable, BlogQuery blogQuery);

    Page<Blog> listBlog(Pageable pageable);

    Page<Blog> listBlog(Pageable pageable,Long tagId);

    Blog saveBlog(Blog blog);

    Blog updateBlog(Long id,Blog newBlog);

    void deleteBlog(Long id );

    //推荐博客列表
    List<Blog> listBlog(Integer size);

    //全局查询:
    Page<Blog>  list(Pageable pageable,String query);

    //将博客内容转为html格式
    Blog getAndConvert(Long id);

    //年份作为key，年份里的blog存入对应的List
    Map<String,List<Blog>> archiveBlog();


    //返回博客条数
    Long countBlog();




}
