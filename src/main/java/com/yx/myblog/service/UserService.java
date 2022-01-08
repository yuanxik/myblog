package com.yx.myblog.service;/*
    @auther
    @create ---
*/

import com.yx.myblog.po.User;

public interface UserService {

    User checkUser(String username,String password);
}
