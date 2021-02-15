package com.Jessie.OnlineAlbum.dao;


import com.Jessie.OnlineAlbum.entity.USER;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.RequestParam;

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
    void save(USER user);

    @Insert("update user set userfid=#{userfid} where username=#{username}")
    void updateUserFid(USER user);

    /**
     * 查询所有
     *
     * @return
     */
    @Select("select * from user")
    List<USER> findAll();

    @Select("select * from user where username= #{username}")
    USER findUser(String username);
}
