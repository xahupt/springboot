package com.percy.projectspring_boot.dto;

import com.percy.projectspring_boot.model.Blog;
import com.percy.projectspring_boot.model.User;
import lombok.Data;

@Data
public class BlogUserDTO {
    private Blog blog;
    private User user;
}
