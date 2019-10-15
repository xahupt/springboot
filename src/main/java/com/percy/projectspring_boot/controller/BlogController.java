package com.percy.projectspring_boot.controller;

import com.alibaba.fastjson.JSONObject;
import com.percy.projectspring_boot.mapper.BlogMapper;
import com.percy.projectspring_boot.model.Blog;
import com.percy.projectspring_boot.service.BlogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;


//这个是blog的控制层
@Controller
public class BlogController {
    @Autowired
    private BlogMapper blogMapper;

    @Autowired
    private BlogService blogService;

    @GetMapping ("/publish")
    public String blog() {

        return "publish";
    }

    @GetMapping ("/publish/{id}")
    public String editBlog(@PathVariable (name = "id") String id,
                           Model model) {
        Blog blog = blogService.FindBlogById(id);
        model.addAttribute("id", id);
        model.addAttribute("title", blog.getTitle());
        ;
        model.addAttribute("content", blog.getContent());
        model.addAttribute("tags", blog.getTags());
        return "publish";
    }

    @PostMapping ("/publish")
    @ResponseBody
    public Object blogpost(@RequestBody JSONObject params) {
        Blog blog = new Blog();
        String title = params.getString("title").trim();
        String content = params.getString("content").trim();
        String tags = params.getString("tags").trim();
        String accountid = params.getString("accountid").trim();
        String id;
        if (params.containsKey("id")) {
            id = params.getString("id").trim();

        } else {
            id = null;
        }
        Map<String, Object> map = new HashMap<>();
        if (title.equals("") || content.equals("")) {
            map.put("success", true);
            map.put("message", "标题和内容不能为空");
            return map;
        }
        blog.setTitle(title);
        blog.setAccountId(accountid);
        blog.setContent(content);
        blog.setTags(tags);

        blogService.InsertOrUpdata(blog, id);
//        blogService.Insert(blog);
        map.put("success", true);
        map.put("message", "更新成功了！");
        return map;
    }
}
