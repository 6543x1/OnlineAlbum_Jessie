package com.Jessie.OnlineAlbum.controller;


import com.Jessie.OnlineAlbum.entity.Folder;
import com.Jessie.OnlineAlbum.entity.USER;
import com.Jessie.OnlineAlbum.service.FolderService;
import com.Jessie.OnlineAlbum.service.impl.userServiceImpl;
import com.sun.org.apache.xpath.internal.operations.Mod;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttributes;

import java.util.List;

@Controller
@RequestMapping("/user")
@SessionAttributes(value = {"list", "username"})
public class TestSpringIocController
{
    @Autowired
    private userServiceImpl userService;
    @Autowired
    private FolderService folderService;

    //使用自动注入
    @RequestMapping("/findAll")
    public String findAll(Model model)
    {
        System.out.println("Controller find ALL...");
        List<USER> list = userService.findAllAccount();
        model.addAttribute("list", list);
        return "list";
        //测试成功
    }

    @RequestMapping("/findOne")
    public String findOne()
    {
        USER user2 = new USER();
        user2.setUsername("Jessie");
        user2.setPassword("123456");
        USER user = userService.findUser(user2.getUsername());
        System.out.println(user.getPassword());
        return "success";
    }


}
