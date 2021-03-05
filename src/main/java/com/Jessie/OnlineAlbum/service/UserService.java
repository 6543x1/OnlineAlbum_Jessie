package com.Jessie.OnlineAlbum.service;


import com.Jessie.OnlineAlbum.entity.User;

import java.util.List;

public interface UserService
{
    /**
     * 保存账户
     *
     * @param user
     */
    void saveUser(User user);

    void updateUserFid(User user);

    /**
     * 查询所有账户
     *
     * @return
     */
    List<User> findAllAccount();

    User findUser(String name);

    void setMailAddr(String username, String mailAddr);

    void editPassword(String username, String password);
}
