package com.percy.projectspring_boot.model;

import lombok.Data;

@Data
public class BlogUser {
    private Blog blog;
    private User user;
}
