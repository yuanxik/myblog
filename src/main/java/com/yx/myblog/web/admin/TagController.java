package com.yx.myblog.web.admin;/*
    @auther
    @create ---
*/

import com.yx.myblog.exception.NotFoundException;
import com.yx.myblog.po.Tag;
import com.yx.myblog.service.TagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.metrics.StartupStep;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;

@Controller
@RequestMapping("/admin")
public class TagController {



    @Autowired
    private TagService tagService;


    @GetMapping("/toTagInput")
    public String saveTag(){

        return "admin/tag-input";

    }
    /*
    分页
     */
    @GetMapping("/toTags")
    public String totags(@PageableDefault(size = 5,sort = {"id"},direction = Sort.Direction.DESC)Pageable pageable,
                         Model model) {

        Page<Tag> tags = tagService.listTags(pageable);
        model.addAttribute("page",tags);

        return "admin/tags";
    }

    /*
    映射到新增页面
     */
    @GetMapping("/tags/input")
    public String toAdd(Model model){
        model.addAttribute("tag",new Tag());
        return "admin/tag-input";
    }
/*
加
 */
    @PostMapping("/toPost")    //RedirectAttributes中加入提示信息
    public String toPost(@Valid Tag tag, BindingResult result, RedirectAttributes redirectAttributes){
                         //@Valid注解表示校验tag对象; BindingResult接受校验后的结果,
                         // Tag tag和BindingResult result中间不能有别的参数
        //重名校验
        if (tagService.getTagByName(tag.getName())!=null){
            result.rejectValue("name","nameError","标签名重复");
        }
        //空name校验
        if (result.hasErrors()){
            return "admin/tag-input";
        }
        Tag tagSave = tagService.saveTag(tag);
        if (tagSave==null){
            redirectAttributes.addFlashAttribute("message","添加失败.");

        }else {
            redirectAttributes.addFlashAttribute("message","添加成功.");

        }
        //重定向
        return "redirect:/admin/toTags";
    }


/*
改 1.从数据库中得到原标签名，并映射到修改页面
 */
    @GetMapping("/tags/{id}/update")
    public String toUpdate_1(@PathVariable Long id,Model model){
       model.addAttribute("tag",tagService.getTag(id));

        return "admin/tag-update";

    }
    /*
    改：2.验证并更新
     */
    @PostMapping("/tags/{id}")    //RedirectAttributes中加入提示信息
    public String toUpdate_2(@Valid Tag newTag, BindingResult result,
                             RedirectAttributes redirectAttributes,
                             @PathVariable Long id){
        //@Valid注解表示校验tag对象; BindingResult接受校验后的结果,
        // Tag tag和BindingResult result中间不能有别的参数
        //重名校验
        Tag byName = tagService.getTagByName(newTag.getName());
        if (byName!=null){
            result.rejectValue("name","nameError","标签名重复");
        }
        //空name校验
        if (result.hasErrors()){
            return "admin/tag-update";
        }
        Tag tagUpdate = tagService.updateTag(id,newTag);
        if (tagUpdate==null){
            redirectAttributes.addFlashAttribute("message","更新失败.");
        }else {
            redirectAttributes.addFlashAttribute("message","更新成功.");

        }
        //重定向
        return "redirect:/admin/toTags";
    }
/*
删
 */
    @GetMapping("/tags/{id}/delete")
    public String toDelete(@PathVariable Long id,RedirectAttributes attributes){


        tagService.deleteTag(id);

        attributes.addFlashAttribute("message","删除成功");
        return "redirect:/admin/toTags";
    }


}
