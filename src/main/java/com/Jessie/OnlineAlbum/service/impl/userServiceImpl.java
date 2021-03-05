package com.Jessie.OnlineAlbum.service.impl;

import com.Jessie.OnlineAlbum.dao.UserDAO;
import com.Jessie.OnlineAlbum.entity.User;
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
    public void saveUser(User user)
    {
        System.out.println("saveUser。。。");
        userDAO.save(user);
    }

    @Override
    public void updateUserFid(User user)
    {
        System.out.println("updateFid...");
        userDAO.updateUserFid(user);
    }

    @Override
    public List<User> findAllAccount()
    {
        System.out.println("findAllAccount...");
        return userDAO.findAll();
    }

    @Override
    public User findUser(String name)
    {
        return userDAO.findUser(name);
    }

    @Override
    public void setMailAddr(String username, String mailAddr)
    {
        userDAO.setMailAddr(username, mailAddr);
    }

    @Override
    public void editPassword(String username, String password)
    {
        userDAO.editPassword(username, password);
    }
}
