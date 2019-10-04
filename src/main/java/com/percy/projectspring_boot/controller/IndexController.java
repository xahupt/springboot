package com.percy.projectspring_boot.controller;

import com.alibaba.fastjson.JSONObject;
import com.percy.projectspring_boot.mapper.BlogMapper;
import com.percy.projectspring_boot.mapper.UserMapper;
import com.percy.projectspring_boot.model.Blog;
import com.percy.projectspring_boot.model.BlogUser;
import com.percy.projectspring_boot.model.User;
import com.sun.javaws.progress.PreloaderDelegate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class IndexController {
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private BlogMapper blogMapper;
    @GetMapping("/")
    public String index(HttpServletRequest request,
                        Model model) {
        List<BlogUser> lists = new ArrayList<>();
        List<Blog> blogList = blogMapper.SelectAllBlog();
        for (Blog blog : blogList) {
            User user1 = userMapper.findUserById(blog.getAccountId());
            BlogUser blogUser = new BlogUser();
            blogUser.setBlog(blog);
            blogUser.setUser(user1);
            lists.add(blogUser);
        }
        model.addAttribute("lists",lists);
        Cookie[] cookies = request.getCookies();
        for (Cookie cookie : cookies) {
            if (cookie.getName().equals("token")) {
                User user = userMapper.findUser(cookie.getValue());
                if (user != null) {
                    request.getSession().setAttribute("user",user);

                    break;
                }

            }
        }
        return "index";
    }

    @GetMapping("/blog")
    public String blog() {

        return "blog";
    }
    @PostMapping("/blog")
    @ResponseBody
    public Object blogpost(@RequestBody JSONObject params) {
        Blog blog = new Blog();
        String title = params.getString("title");
        String content = params.getString("content");
        String tags = params.getString("tags");
        String accountid = params.getString("accountid");
        blog.setTitle(title);
        blog.setAccountId(accountid);
        blog.setContent(content);
        blog.setTags(tags);
        blog.setGmtCreate(System.currentTimeMillis());
        blog.setGmtModified(blog.getGmtCreate());
        blog.setCommentCount(0);
        blog.setLikeCount(0);
        blog.setViewCount(0);

        blogMapper.insert(blog);
        Map<String,Object> map = new HashMap<>();
        map.put("success",true);
        map.put("message","更新成功了！");
        return map;
    }
    @GetMapping("/logout")
    public String logout(HttpServletRequest request,
                         HttpServletResponse response) {
        request.getSession().removeAttribute("token");
        response.addCookie(new Cookie("token",""));
        request.getSession().removeAttribute("user");
        return "redirect:/";
    }
}
