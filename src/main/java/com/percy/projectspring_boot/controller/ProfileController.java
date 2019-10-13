package com.percy.projectspring_boot.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.Mapping;

@Controller
public class ProfileController {

    @GetMapping ("/profile/question")
    public String Question() {

        return "question";
    }

    @GetMapping ("profile/message")
    public String Message() {

        return "message";
    }

}
