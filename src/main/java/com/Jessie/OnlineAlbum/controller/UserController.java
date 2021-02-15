package com.Jessie.OnlineAlbum.controller;

import com.Jessie.OnlineAlbum.dao.UserDAO;
import com.Jessie.OnlineAlbum.entity.Folder;
import com.Jessie.OnlineAlbum.entity.USER;
import com.Jessie.OnlineAlbum.service.FolderService;
import com.Jessie.OnlineAlbum.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.support.SessionStatus;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.util.List;

@Controller
@RequestMapping("/user")
@SessionAttributes(value = {"username", "userfid"}, types = {String.class, Integer.class})
//一定要写上面那句话啊！！！不然没存到里面去的啊！！！
public class UserController
{
    @Autowired
    private UserService userService;//别忘记注入数值了！怪不得NullPointer
    @Autowired
    private FolderService folderService;

    @RequestMapping(value = "/login", method = {RequestMethod.GET, RequestMethod.POST})
    @ResponseBody
    public String getUser(USER user, Model model)
    {
        System.out.println("已经取得用户数据，正在向数据库查询。。。");
        System.out.println(user.getUsername());
        try
        {
            if (userService.findUser(user.getUsername()).getPassword().equals(user.getPassword()))
            {

                model.addAttribute("username", user.getUsername());
                model.addAttribute("userfid", userService.findUser(user.getUsername()).getUserfid());
                return "loginSuccess";
            }
        } catch (NullPointerException e)
        {
            e.printStackTrace();//用户不存在
        }
        System.out.println("Wrong Password Or Username");
        return "error";
    }

    @RequestMapping("/Register")
    @ResponseBody
    public String register(USER user)
    {
        System.out.println(user);
        System.out.println("取得注册用数据，开始向数据库中写入数据...");
        if (userService.findUser(user.getUsername()) != null)
        {
            System.out.println("用户已存在，请重试");
            return "existedUsername";
        }
        userService.saveUser(user);
        FolderController folderController = new FolderController();
        System.out.println("开始创建用户文件夹...");//这样写好像耦合度很高但不知道怎么改
        Folder userFolder = new Folder();
        try
        {
            userFolder.setUsername(user.getUsername());
            userFolder.setSize(0);
            userFolder.setFather(0);
            userFolder.setFolderName(user.getUsername());
            Folder theFather = folderService.getFolder(0);
            userFolder.setPath(theFather.getPath() + user.getUsername());
            File file = new File(userFolder.getPath());
            if (!file.exists())
            {
                file.mkdirs();
            }
            folderService.newFolder(userFolder);
            System.out.println("创建成功！");
        } catch (NullPointerException e)
        {
            e.printStackTrace();
            return "error";
        }
        userFolder = folderService.queryFolder(userFolder.getPath(), userFolder.getUsername());
        user.setUserfid(userFolder.getFid());
        userService.updateUserFid(user);
        return "RegisterSuccess";

    }

    @RequestMapping("/tologin")
    public String toLogin()
    {
        System.out.println("正在跳转到登入页");
        return "login";
    }

    @RequestMapping("/testRedirect")
    public void Redirect(HttpServletRequest request, HttpServletResponse response)
    {
        try
        {
            response.sendRedirect(request.getContextPath() + "/index.jsp");
            //成功重定向到本页！地址栏内容已经改变，但是好像只有index中能用。。。
        } catch (IOException e)
        {
            e.printStackTrace();
        }
    }

    @RequestMapping("/ToUpload")
    public void RedirectToUpload(HttpServletRequest request, HttpServletResponse response)
    {
        try
        {
            response.sendRedirect(request.getContextPath() + "/FileUpload.jsp");
        } catch (IOException e)
        {
            e.printStackTrace();
        }
    }

    //模拟异步请求响应
    @RequestMapping("/testJson")
    public void testAjax(@RequestBody String body)
    {
        System.out.println("执行ajax请求");
        System.out.println(body);
        //  这样可以成功取得Json的内容
    }

    @RequestMapping("/isLogin")
    public String isLogin(ModelMap modelMap)
    {
        System.out.println((String) modelMap.get("username"));
        return "success";
    }

    @RequestMapping("/getSessionAttribute")
    public String getSessionAttribute(ModelMap modelmap)
    {
        String msg = (String) modelmap.get("username");
        System.out.println(msg);
        return "success";
    }

    @RequestMapping("/delSessionAttribute")
    public void delSessionAttribute(SessionStatus status)
    {
        System.out.println("恢复Session");
        status.setComplete();//将session恢复
    }
}
