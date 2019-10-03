package com.percy.projectspring_boot.controller;

import com.percy.projectspring_boot.mapper.UserMapper;
import com.percy.projectspring_boot.model.User;
import jdk.nashorn.internal.parser.Token;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Controller
public class IndexController {
    @Autowired
    private UserMapper userMapper;
    @GetMapping("/")
    public String index(HttpServletRequest request) {
        Cookie[] cookies = request.getCookies();
        for (Cookie cookie : cookies) {
            if (cookie.getName().equals("token")) {
                User user = userMapper.findUser(cookie.getValue());
                if (user != null) {
                    request.getSession().setAttribute("user",user);
                }
                break;
            }
        }


        return "index";
    }
}
