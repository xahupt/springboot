package com.percy.projectspring_boot.controller;

import com.percy.projectspring_boot.mapper.BlogMapper;
import com.percy.projectspring_boot.mapper.UserMapper;
import com.percy.projectspring_boot.model.Pages;
import com.percy.projectspring_boot.service.BlogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Controller
public class IndexController {
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private BlogMapper blogMapper;

    @Autowired
    private BlogService blogService;

    @GetMapping("/")
    public String index(Model model,
                        @RequestParam(name = "page", defaultValue = "1") Integer page,
                        @RequestParam(name = "size", defaultValue = "5") Integer size) {


        //因为首页需要添加blog的列表，所以这里增加了blogUserDTO这样一个类，里面包含了blog和user
        //BlogService主要是为了实现DTO层
        Pages pages = blogService.list(page, size);
        model.addAttribute("pages", pages);
        return "index";
    }


    @GetMapping("/logout")
    public String logout(HttpServletRequest request,
                         HttpServletResponse response) {
        request.getSession().removeAttribute("token");
        response.addCookie(new Cookie("token", ""));
        request.getSession().removeAttribute("user");
        return "redirect:/";
    }
}
