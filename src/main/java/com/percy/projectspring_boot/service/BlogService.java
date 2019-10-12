package com.percy.projectspring_boot.service;

import com.percy.projectspring_boot.dto.BlogUserDTO;
import com.percy.projectspring_boot.mapper.BlogMapper;
import com.percy.projectspring_boot.mapper.UserMapper;
import com.percy.projectspring_boot.model.Blog;
import com.percy.projectspring_boot.model.Pages;
import com.percy.projectspring_boot.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class BlogService {
    @Autowired
    private UserMapper userMapper;

    @Autowired
    private BlogMapper blogMapper;

    private List<BlogUserDTO> getBlogUserDTO(List<Blog> blogs){
        List<BlogUserDTO> result = new ArrayList<>();
        for (Blog blog : blogs) {
            User user = userMapper.findUserById(blog.getAccountId());
            BlogUserDTO blogUserDTO = new BlogUserDTO();
            blogUserDTO.setBlog(blog);
            blogUserDTO.setUser(user);
            result.add(blogUserDTO);
        }
        return result;

    }


    public Pages list(Integer page, Integer size) {
        int totalCount = blogMapper.GetBlogCount();
        int pos = (page-1)*size;
        List<Blog> blogs = blogMapper.getBlogList(size,pos);
        Pages pages = new Pages();
        List<BlogUserDTO> result = getBlogUserDTO(blogs);
        pages.setBlogUserDTOList(result);
        pages.setPages(totalCount,page,size);
        return pages;
    }
}
