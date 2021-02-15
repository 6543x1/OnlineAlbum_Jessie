package com.Jessie.OnlineAlbum.service;


import com.Jessie.OnlineAlbum.entity.USER;

import java.util.List;

public interface UserService
{
    /**
     * 保存账户
     *
     * @param user
     */
    void saveUser(USER user);

    void updateUserFid(USER user);

    /**
     * 查询所有账户
     *
     * @return
     */
    List<USER> findAllAccount();

    USER findUser(String name);
}
