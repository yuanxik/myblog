package com.yx.myblog.web.admin;/*
    @auther
    @create ---
*/

import com.yx.myblog.po.Type;
import com.yx.myblog.service.TypeService;
import org.springframework.beans.factory.annotation.Autowired;
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
public class TypeController {

    @Autowired
    private TypeService typeService;

    /*
    分页
     */
    @GetMapping("/toTypes")
    public String types(@PageableDefault(size = 5, sort = {"id"}, direction = Sort.Direction.DESC) Pageable pageable,
                        Model model) {   //注意Pageable的包要正确
        Page<Type> types = typeService.listType(pageable);
        model.addAttribute("page", types);

        return "admin/types";
    }

/*
映射到新增页面
 */
    @GetMapping("/toTypes/input")
    public String input(Model model) {

        model.addAttribute("type", new Type());
        return "admin/type-input";
    }

    /*
    加
     */
    @PostMapping("/toTypesPost")
    public String post(@Valid Type newType, BindingResult result, RedirectAttributes attributes) {

        Type type1 = typeService.getTypeByName(newType.getName());
        //重name校验
        if (type1 != null) {
            //field属性与type类中的name属性保持一致
            result.rejectValue("name", "nameError", "分类名称已存在.");
        }

        //空名校验
        if (result.hasErrors()) {
            return "admin/type-input";
        }
        Type saveType = typeService.saveType(newType);
        if (saveType == null) {
            attributes.addFlashAttribute("message", "新增失败.");
        } else {
            attributes.addFlashAttribute("message", "新增成功.");
        }
        return "redirect:/admin/toTypes";
    }

    /*
    改 1.
     */
    @GetMapping("/types/{id}/update")
    public String editInput(@PathVariable Long id, Model model) {

        model.addAttribute("type", typeService.getType(id));
        return "admin/type-update";
    }
/*
2.
 */
    @PostMapping("/types/{id}")    //type参数和result必须相邻，中间不能有别的参数
    public String editpost(@Valid Type newType, BindingResult result, @PathVariable Long id,
                           RedirectAttributes attributes) {

        Type typeByName = typeService.getTypeByName(newType.getName());
        if (typeByName != null) {
            //field属性与type类中的name属性保持一致
            result.rejectValue("name", "nameError", "分类名称已存在.");
        }

        if (result.hasErrors()) {
            return "admin/type-update";
        }
        Type saveType = typeService.updateType(id, newType);
        if (saveType == null) {
            attributes.addFlashAttribute("message", "更新失败.");
        } else {
            attributes.addFlashAttribute("message", "更新成功.");
        }
        return "redirect:/admin/toTypes";
    }

/*
删
 */
    @GetMapping("/types/{id}/delete")
    public String delete(@PathVariable Long id, RedirectAttributes attributes) {
        typeService.deleteType(id);

        attributes.addFlashAttribute("message", "删除成功");

        return "redirect:/admin/toTypes";

    }


}
