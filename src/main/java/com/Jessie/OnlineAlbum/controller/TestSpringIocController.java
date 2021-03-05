package com.Jessie.OnlineAlbum.controller;


import com.Jessie.OnlineAlbum.entity.User;
import com.Jessie.OnlineAlbum.service.FolderService;
import com.Jessie.OnlineAlbum.service.MailService;
import com.Jessie.OnlineAlbum.service.impl.userServiceImpl;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.DigestUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttributes;

import java.io.File;
import java.util.List;

@Controller
@RequestMapping("/test")
@SessionAttributes(value = {"list", "username"})
public class TestSpringIocController
{
    @Autowired
    private userServiceImpl userService;
    @Autowired
    private FolderService folderService;
    @Autowired
    private MailService mailService;

    //使用自动注入
    @RequestMapping("/findAll")
    public String findAll(Model model)
    {
        System.out.println("Controller find ALL...");
        List<User> list = userService.findAllAccount();
        model.addAttribute("list", list);
        return "list";
        //测试成功
    }

    @RequestMapping("/findOne")
    public String findOne()
    {
        User user2 = new User();
        user2.setUsername("Jessie");
        user2.setPassword("123456");
        User user = userService.findUser(user2.getUsername());
        System.out.println(user.getPassword());
        return "success";
    }

    @RequestMapping("/mailSend")
    @ResponseBody
    public String sendMail()
    {

        mailService.sendResetPw("1647389906@qq.com", "Hello World");
        return "success";
    }

    @RequestMapping("/encrypt")
    @ResponseBody
    public String encryptPw() throws Exception
    {
        List<User> userList = userService.findAllAccount();
        for (User user : userList)
        {
            if (user.getUsername().equals("Jessie"))
            {
                continue;
            } else
            {
                userService.editPassword(user.getUsername(), DigestUtils.md5DigestAsHex(user.getPassword().getBytes()));
            }
        }
        return "success";
    }

    @Test
    public void run2()
    {
        File file = new File("D:\\TomcatImg\\Jessie\\my\\DDD");
        file.delete();
    }


}
