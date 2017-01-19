package com.freedom.weixin.utils;


import com.freedom.weixin.entity.User;

import javax.servlet.http.HttpSession;

/**
 * @Author: mihuajun 【kobe96688@126.com】
 * @Date: 1/17/2017 4:32 PM
 */

public class UserUtils {
    public static User getCurrent(HttpSession httpSession){
        User user = (User) httpSession.getAttribute("user");
        if( user == null){
            user = new User();
            httpSession.setAttribute("user",user);
        }
        return user;
    }
    public static void clear(HttpSession httpSession){
        httpSession.removeAttribute("user");
    }
}
