package com.percy.projectspring_boot.mapper;

import com.percy.projectspring_boot.model.User;
import org.apache.ibatis.annotations.*;

import java.util.List;


@Mapper
public interface UserMapper {
    @Insert("Insert into user (account_id,name,token,gmt_create,gmt_modified,src_url) " +
            "values (#{accountId},#{name},#{token},#{gmtCreate},#{gmtModified},#{srcUrl})")
    void insert(User user);

    @Select("select  * from user where token=#{token}")
    User findUser(@Param("token") String token);

    @Select("select  * from user where account_id=#{accountId}")
    User findUserById(@Param("accountId") String accountId);

    @Select("Select * from user limit #{count} offset #{pos}")
    List<User> getUserList(@Param("count") int count,@Param("pos") int pos);

    @Update("Update user set name=#{name},token=#{token},gmt_modified=#{gmtModified},src_url=#{srcUrl} where account_id=#{accountId}")
    int updateUser(User user);
}
