package com.percy.projectspring_boot.mapper;

import com.percy.projectspring_boot.model.Blog;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface BlogMapper {
    @Insert("Insert into blog (account_id,title,content,tags,gmt_create,gmt_modified,comment_count,view_count,like_count) values (#{accountId},#{title},#{content},#{tags},#{gmtCreate},#{gmtModified},#{commentCount},#{viewCount},#{likeCount})")
    void insert(Blog blog);
}
