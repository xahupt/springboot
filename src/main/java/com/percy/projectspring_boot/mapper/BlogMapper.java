package com.percy.projectspring_boot.mapper;

import com.percy.projectspring_boot.model.Blog;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface BlogMapper {
    @Insert("Insert into blog (account_id,title,content,tags,gmt_create,gmt_modified,comment_count,view_count,like_count) values (#{accountId},#{title},#{content},#{tags},#{gmtCreate},#{gmtModified},#{commentCount},#{viewCount},#{likeCount})")
    void insert(Blog blog);

    @Select("Select * from blog")
    List<Blog> SelectAllBlog();

    @Select("Select count(1) from blog")
    int GetBlogCount();

    @Select("Select * from blog limit #{count} offset #{pos}")
    List<Blog> getBlogList(@Param("count") int count, @Param("pos") int pos);

//    @Select("Select * from blog where account_id=#{accountId} limit #{count} offset #{pos}")
//    List<Blog> getBlogListById(@Param("accountId") int accountId,@Param("count") int count, @Param("pos") int pos);

    @Select("Select * from blog where account_id=#{accountId}")
    List<Blog> getBlogListById(@Param("accountId") int accountId);

}
