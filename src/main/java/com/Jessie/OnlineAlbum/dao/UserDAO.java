package com.Jessie.OnlineAlbum.dao;


import com.Jessie.OnlineAlbum.entity.User;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserDAO
{
    /**
     * 保存
     *
     * @param user
     */
    @Insert("insert into user (username,password) values (#{username},#{password})")
//里面是大括号
    void save(User user);

    @Insert("update user set userfid=#{userfid} where username=#{username}")
    void updateUserFid(User user);

    /**
     * 查询所有
     *
     * @return
     */
    @Select("select * from user")
    List<User> findAll();

    @Select("select * from user where username= #{username}")
    User findUser(String username);

    @Update("update user set password=#{password} where username=#{username}")
    void editPassword(@Param("username") String username, @Param("password") String password);

    @Update("update user set mailAddr=#{mailAddr} where username=#{username}")
    void setMailAddr(String username, String mailAddr);
}
