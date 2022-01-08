package com.yx.myblog.service;/*
    @auther
    @create ---
*/

import com.yx.myblog.dao.BlogRepository;
import com.yx.myblog.exception.NotFoundException;
import com.yx.myblog.po.Blog;
import com.yx.myblog.po.Type;
import com.yx.myblog.util.MarkdownUtils;
import com.yx.myblog.vo.BlogQuery;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.criteria.*;
import java.util.*;

@Service
public class BlogServiceImpl implements BlogService {


    @Autowired
    private BlogRepository blogRepository;

    @Transactional
    @Override
    public Blog getBlog(Long id) {
        return blogRepository.findById(id).get();
    }
    /*
    分页查询方法
     */
    @Transactional
    @Override
    public Page<Blog> listBlog(Pageable pageable, BlogQuery blog) {

        Page<Blog> blogPage = blogRepository.findAll(new Specification<Blog>() {

            /*
            toPredicate方法帮助处理动态查询的条件:
            root:要查询的对象
            CriteriaQuery<?> cq:存放查询条件的容器.
            CriteriaBuilder cb:设置具体某一个条件的表达式，例如两条件相等，模糊查询like等
             */
            @Override
            public Predicate toPredicate(Root<Blog> root, CriteriaQuery<?> cq, CriteriaBuilder cb) {
                List<Predicate> predicates = new ArrayList<>();
                if (!"".equals(blog.getTitle()) && blog.getTitle() != null) {
                    //title like %blog.getTitle()%
                    predicates.add(cb.like(root.<String>get("title"), "%" + blog.getTitle() + "%"));
                }
                if (blog.getTypeId() != null) {
                    //id equal blog.getType().getId())
                    predicates.add(cb.equal(root.<Type>get("type").get("id"), blog.getTypeId()));
                }
                if (blog.isRecommend()) {
                    //recommend  equal blog.isRecommend())
                    predicates.add(cb.equal(root.<Boolean>get("recommend"), blog.isRecommend()));
                }
                cq.where(predicates.toArray(new Predicate[predicates.size()]));
                return null;
            }
        }, pageable);
        return blogPage;
    }

    @Override
    public Page<Blog> listBlog(Pageable pageable) {
        return blogRepository.findAll(pageable);
    }

    @Override
    public Page<Blog> listBlog(Pageable pageable, Long tagId) {
        Page<Blog> page = blogRepository.findAll(new Specification<Blog>() {
            @Override
            public Predicate toPredicate(Root<Blog> root, CriteriaQuery<?> cq, CriteriaBuilder cb) {
                Join<Object, Object> join = root.join("tags");
                return cb.equal(join.get("id"), tagId);
            }
        }, pageable);
        return page;
    }

    @Transactional
    @Override
    public Blog saveBlog(Blog blog) {
        if (blog.getId()==null){
            //发布时间
            blog.setCreateTime(new Date());
            //更新时间
            blog.setUpdateTime(new Date());
            //初始浏览次数
            blog.setView(0);
        }else {
            blog.setUpdateTime(new Date());
        }


        return blogRepository.save(blog);
    }
    @Transactional
    @Override
    public Blog updateBlog(Long id, Blog newBlog) {

        Blog blog = blogRepository.findById(id).get();
        if (blog==null){
            throw new NotFoundException("查询的博客不存在");
        }
        BeanUtils.copyProperties(newBlog,blog);
        return blog;
    }
    @Transactional
    @Override
    public void deleteBlog(Long id) {

        blogRepository.deleteById(id);

    }

    @Override
    public List<Blog> listBlog(Integer size) {

        Sort sort = Sort.by(Sort.Direction.DESC, "updateTime");
        Pageable pageable = PageRequest.of(0, size, sort);
        return blogRepository.findTop(pageable);
    }

    @Override
    public Page<Blog> list(Pageable pageable, String query) {

        Page<Blog> blogPage = blogRepository.findByQuery(pageable, query);
        return  blogPage;
    }

    @Override
    public Blog getAndConvert(Long id) {
        Blog blog = blogRepository.findById(id).get();
        if (blog==null){
            throw new NotFoundException("博客不存在.");
        }
        Blog b = new Blog();
        BeanUtils.copyProperties(blog,b);
        String content = b.getContent();
        b.setContent(MarkdownUtils.markdownToHtmlExtensions(content));
        return b;
    }

    @Override
    public Map<String, List<Blog>> archiveBlog() {

        List<String> years = blogRepository.findGroupYear();
        HashMap<String, List<Blog>> hashMap = new HashMap<>();
        for (String year:years){
            hashMap.put(year,blogRepository.findByYear(year));
        }
        return hashMap;
    }

    @Override
    public Long countBlog() {
        return blogRepository.count();
    }
}
