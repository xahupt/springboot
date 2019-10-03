package com.percy.projectspring_boot.mapper;

import com.percy.projectspring_boot.model.User;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;


@Mapper
public interface UserMapper {
    @Insert("Insert into user (account_id,name,token,gmt_create,gmt_modified,src_url) " +
            "values (#{accountId},#{name},#{token},#{gmtCreate},#{gmtModified},#{srcUrl})")
    void insert(User user);

    @Select("select  * from user where token=#{token}")
    User findUser(@Param("token") String token);

    @Select("select  * from user where account_id=#{accountId}")
    User findUserById(@Param("accountId") String accountId);
}
