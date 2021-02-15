package com.Jessie.OnlineAlbum.service.impl;

import com.Jessie.OnlineAlbum.dao.UserDAO;
import com.Jessie.OnlineAlbum.entity.USER;
import com.Jessie.OnlineAlbum.service.UserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("userService")
public class userServiceImpl implements UserService
{
    @Autowired
    private UserDAO userDAO;

    @Override
    public void saveUser(USER user)
    {
        System.out.println("saveUser。。。");
        userDAO.save(user);
    }

    @Override
    public void updateUserFid(USER user)
    {
        System.out.println("updateFid...");
        userDAO.updateUserFid(user);
    }

    @Override
    public List<USER> findAllAccount()
    {
        System.out.println("findAllAccount...");
        return userDAO.findAll();
    }

    @Override
    public USER findUser(String name)
    {
        return userDAO.findUser(name);
    }
}
