package com.percy.projectspring_boot.service;

import com.percy.projectspring_boot.dto.BlogUserDTO;
import com.percy.projectspring_boot.mapper.BlogMapper;
import com.percy.projectspring_boot.mapper.UserMapper;
import com.percy.projectspring_boot.model.*;
import org.apache.ibatis.session.RowBounds;
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

    private List<BlogUserDTO> getBlogUserDTO(List<Blog> blogs) {
        List<BlogUserDTO> result = new ArrayList<>();
        for (Blog blog : blogs) {

            UserExample userExample = new UserExample();
            userExample.createCriteria()
                    .andAccountIdEqualTo(blog.getAccountId());
            List<User> user = userMapper.selectByExample(userExample);
            BlogUserDTO blogUserDTO = new BlogUserDTO();
            blogUserDTO.setBlog(blog);
            blogUserDTO.setUser(user.get(0));
            result.add(blogUserDTO);
        }
        return result;

    }


    public Pages list(Integer page, Integer size, Integer accountId) {
        BlogExample blogExample = new BlogExample();
//        blogExample.createCriteria().andCommentCountIsNotNull();
        blogExample.createCriteria().andIdIsNotNull();
        long totalCount = blogMapper.selectByExample(blogExample).size();
//        long totalCount = 1;
        int pos = (page - 1) * size;
        List<Blog> blogs;
        if (accountId == 0) {
            blogExample = new BlogExample();
            blogExample.createCriteria();
            blogs = blogMapper.selectByExampleWithRowbounds(blogExample, new RowBounds(pos,size));
            //blogs = blogMapper.selectByExample(blogExample);

        } else {
            blogExample.clear();
            blogExample.createCriteria()
                    .andAccountIdEqualTo(String.valueOf(accountId));
            blogs = blogMapper.selectByExample(blogExample);
        }

        Pages pages = new Pages();
        List<BlogUserDTO> result = getBlogUserDTO(blogs);
        pages.setBlogUserDTOList(result);
        pages.setPages(totalCount, page, size);
        return pages;
    }

    public void Insert(Blog blog) {
        blogMapper.insert(blog);
    }

    public Blog FindBlogById(String id) {
        return blogMapper.selectByPrimaryKey(Integer.valueOf(id));
    }

    public void InsertOrUpdata(Blog blog, String id) {
        if (id == "") {
            //插入
            blog.setGmtCreate(System.currentTimeMillis());
            blog.setGmtModified(blog.getGmtCreate());
            blog.setCommentCount(0);
            blog.setLikeCount(0);
            blog.setViewCount(0);
            blogMapper.insert(blog);
        } else {
            //更新
            blog.setId(Integer.parseInt(id));
            blog.setGmtModified(System.currentTimeMillis());
            BlogExample blogExample = new BlogExample();
            blogExample.createCriteria().andIdEqualTo(blog.getId());
            blogMapper.updateByExample(blog, blogExample);
        }
    }
}
