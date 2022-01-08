package com.yx.myblog.interceptor;/*
    @auther
    @create ---
*/

import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


/*
拦截器：拦截未登录的用户
 */
public class LoginInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request,
                             HttpServletResponse response,
                             Object handler) throws Exception {

        if (request.getSession().getAttribute("user")==null){
            response.sendRedirect("/admin");
            return false;
        }
        return true;
    }
}
