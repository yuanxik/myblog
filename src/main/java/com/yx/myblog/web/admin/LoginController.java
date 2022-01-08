package com.yx.myblog.web.admin;/*
    @auther
    @create ---
*/

import com.yx.myblog.po.User;
import com.yx.myblog.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.annotation.Resource;
import javax.print.attribute.standard.PresentationDirection;
import javax.servlet.http.HttpSession;

@Controller
@RequestMapping("/admin")
public class LoginController {

    @Autowired
    private UserService userService;


    @GetMapping   //未设置具体访问路径，默认全局路径/admin
    public String loginPage(){
        return "admin/login";
    }

    @PostMapping("/login")
    public String login(@RequestParam String username,
                        @RequestParam String password,
                        HttpSession session,
                        RedirectAttributes attributes){
        User user = userService.checkUser(username, password);
        if (user!=null){
            user.setPassword(null);  //在页面拿到密码很不安全
            session.setAttribute("user",user);
            return "admin/index";
        }else {
            attributes.addFlashAttribute("message","用户名或者密码错误.");
            return "redirect:/admin";
        }
    }


    @GetMapping("/logout")
    public String logout(HttpSession session){
        session.removeAttribute("user");

        return "redirect:/admin";
    }
}
