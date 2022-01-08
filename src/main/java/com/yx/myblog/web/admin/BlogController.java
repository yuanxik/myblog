package com.yx.myblog.web.admin;/*
    @auther
    @create ---
*/

import com.yx.myblog.po.Blog;
import com.yx.myblog.po.Tag;
import com.yx.myblog.po.Type;
import com.yx.myblog.po.User;
import com.yx.myblog.service.BlogService;
import com.yx.myblog.service.TagService;
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
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
@RequestMapping("/admin")
public class BlogController {

    private static final String INPUT = "admin/blog-input";
    private static final String LIST = "admin/blogs";
    private static final String REDIRECT_LIST = "redirect:/admin/toBlogs";

    @Autowired
    private BlogService blogService;

    @Autowired
    private TypeService typeService;
    @Autowired
    private TagService tagService;

    /*
    分页
     */
    @GetMapping("/toBlogs")
    public String list(@PageableDefault(size = 2, sort = {"updateTime"}, direction = Sort.Direction.DESC) Pageable pageable,
                       Model model,
                       BlogQuery blog) {
        Page<Blog> blogs = blogService.listBlog(pageable, blog);
        List<Type> types = typeService.listType();
        model.addAttribute("page", blogs);
        model.addAttribute("types", types);
        return LIST;
    }

    @PostMapping("/toBlogs/search")
    public String search(@PageableDefault(size = 2, sort = {"updateTime"}, direction = Sort.Direction.DESC) Pageable pageable,
                         Model model,
                         BlogQuery blog
    ) {
        Page<Blog> blogs = blogService.listBlog(pageable, blog);
        model.addAttribute("page", blogs);
        //只返回admin/blogs页面下的blogList片段
        return "admin/blogs::blogList";
    }

    /*
    映射到新增博客页面
     */
    @GetMapping("/toWrite")
    public String toPublishPage(Model model) {

        getTypeAndTag(model);
        model.addAttribute("blog", new Blog());
        return INPUT;
    }

    /*
    映射到修改博客页面
     */
    @GetMapping("/toWrite/{id}")
    public String toUpdatePage(@PathVariable Long id, Model model) {

        getTypeAndTag(model);
        Blog blog = blogService.getBlog(id);
        //将所有标签的id值抽取出来，并转为一个String
        blog.tagIdsInit();
        model.addAttribute("blog", blog);
        return INPUT;
    }

    /*
    获取全部标签和分类
     */
    private void getTypeAndTag(Model model) {
        //博客新增页面需要查询到分类
        List<Type> types = typeService.listType();
        model.addAttribute("types", types);
        //博客新增页面需要查询到标签
        List<Tag> tags = tagService.listTag();
        model.addAttribute("tags", tags);
    }

    /*
    发布新博客
     */
    @PostMapping("/toPublish")
    public String toPublish(Blog blog, RedirectAttributes attributes, HttpSession session) {

        blog.setUser((User) session.getAttribute("user"));
        blog.setType(typeService.getType(blog.getType().getId()));
        blog.setTags(tagService.listTag(blog.getTagIds()));

        Blog saveBlog = blogService.saveBlog(blog);

        if (saveBlog == null) {
            attributes.addFlashAttribute("message", "操作失败");
        } else {
            attributes.addFlashAttribute("message", "操作成功");

        }
        return REDIRECT_LIST;

    }

    /*
    删
     */
    @GetMapping("/toDelete/{id}")
    public String toDelete(@PathVariable Long id, RedirectAttributes attributes) {
        blogService.deleteBlog(id);
        attributes.addFlashAttribute("message", "删除成功");
        return REDIRECT_LIST;
    }


}
