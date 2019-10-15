package com.percy.projectspring_boot.controller;

import com.percy.projectspring_boot.model.Pages;
import com.percy.projectspring_boot.service.BlogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletResponse;

@Controller
public class ProfileController {

    @Autowired
    private BlogService blogService;
    @GetMapping ("/profile/question")
    public String Question(Model model,
                           @RequestParam (name = "page", defaultValue = "1") Integer page,
                           @RequestParam (name = "size", defaultValue = "5") Integer size,
                           @RequestParam (name = "account_id", defaultValue = "") Integer accountId) {
        Pages pages = blogService.list(page, size, accountId);
        model.addAttribute("pages", pages);
        model.addAttribute("profile", "我的问题");
        return "profile";
    }

    @GetMapping ("/profile/message")
    public String Message(Model model) {
        model.addAttribute("profile", "我的消息");
        return "profile";
    }
    @GetMapping ("/profile/system_msg")
    public String SystemMsg(Model model) {
        model.addAttribute("profile", "系统消息");
        return "profile";
    }
}
