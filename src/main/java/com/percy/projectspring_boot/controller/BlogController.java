package com.percy.projectspring_boot.controller;

import com.alibaba.fastjson.JSONObject;
import com.percy.projectspring_boot.mapper.BlogMapper;
import com.percy.projectspring_boot.model.Blog;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.Map;


//这个是发布blog的控制层
@Controller
public class BlogController {
    @Autowired
    private BlogMapper blogMapper;

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
        Map<String, Object> map = new HashMap<>();
        map.put("success", true);
        map.put("message", "更新成功了！");
        return map;
    }
}
