package com.percy.projectspring_boot.mapper;

import com.percy.projectspring_boot.model.Blog;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface BlogMapper {
    @Insert ("Insert into blog (account_id,title,content,tags,gmt_create,gmt_modified,comment_count,view_count,like_count) values (#{accountId},#{title},#{content},#{tags},#{gmtCreate},#{gmtModified},#{commentCount},#{viewCount},#{likeCount})")
    void insert(Blog blog);

    @Select ("Select * from blog")
    List<Blog> SelectAllBlog();

    @Select ("Select count(1) from blog")
    int GetBlogCount();

    @Select ("Select * from blog limit #{count} offset #{pos}")
    List<Blog> getBlogList(@Param ("count") int count, @Param ("pos") int pos);

//    @Select("Select * from blog where account_id=#{accountId} limit #{count} offset #{pos}")
//    List<Blog> getBlogListById(@Param("accountId") int accountId,@Param("count") int count, @Param("pos") int pos);

    @Select ("Select * from blog where account_id=#{accountId}")
    List<Blog> getBlogListById(@Param ("accountId") int accountId);

    @Select ("Select * from blog where id=#{id}")
    Blog findById(String id);

    @Update("Update blog set title=#{title},content=#{content},tags=#{tags},gmt_modified=#{gmtModified} where id = #{id}")
    int updateBlog(Blog blog);
}
