package com.Jessie.OnlineAlbum.controller;

import com.Jessie.OnlineAlbum.entity.Folder;
import com.Jessie.OnlineAlbum.entity.Result;
import com.Jessie.OnlineAlbum.entity.User;
import com.Jessie.OnlineAlbum.service.FolderService;
import com.Jessie.OnlineAlbum.service.MailService;
import com.Jessie.OnlineAlbum.service.UserService;
import com.Jessie.OnlineAlbum.service.impl.mailServiceImpl;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.util.DigestUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.support.SessionStatus;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.IOException;

@Controller
@RequestMapping("/user")
@SessionAttributes(value = {"username", "userfid", "userPath", "resetCode", "mailAddr"}, types = {String.class, Integer.class, String.class, String.class, String.class})
//一定要写上面那句话啊！！！不然没存到里面去的啊！！！
public class UserController
{
    @Autowired
    ObjectMapper objectMapper = new ObjectMapper();
    @Autowired
    private UserService userService;//别忘记注入数值了！怪不得NullPointer
    @Autowired
    private FolderService folderService;
    @Autowired
    private MailService mailService;

    @RequestMapping(value = "/login", method = {RequestMethod.GET, RequestMethod.POST})
    @ResponseBody
    public String login(User user, Model model, HttpServletRequest request, HttpServletResponse response, HttpSession session) throws Exception
    {
        System.out.println("已经取得用户数据，正在向数据库查询。。。");
        System.out.println(user.toString());
        Result result = null;
        User thisUser;
        try
        {
            thisUser = userService.findUser(user.getUsername());
            if (thisUser.getPassword().equals(DigestUtils.md5DigestAsHex(user.getPassword().getBytes())))
            {

                model.addAttribute("username", user.getUsername());
                model.addAttribute("userfid", thisUser.getUserfid());
                model.addAttribute("userPath", folderService.getFolder(thisUser.getUserfid()).getPath());
                System.out.println("userPath:" + folderService.getFolder(thisUser.getUserfid()).getPath());
                if (session != null)
                {
                    System.out.println("NOT NULL SESSION");
                    session.setMaxInactiveInterval(30 * 60);
                }
                result = Result.success("loginSuccess", userService.findUser(user.getUsername()).getUserfid());
            } else
            {
                result = Result.error("用户名或者密码错误");
            }
        } catch (NullPointerException e)
        {
            e.printStackTrace();//用户不存在
            System.out.println("Wrong Password Or Username");
            result = Result.error("Wrong Password or Username");
        }
        return objectMapper.writeValueAsString(result);
    }

    @RequestMapping("/Register")
    @ResponseBody
    public String register(User user) throws Exception
    {
        System.out.println(user);
        System.out.println("取得注册用数据，开始向数据库中写入数据...");
        if (userService.findUser(user.getUsername()) != null)
        {
            System.out.println("用户已存在，请重试");
            return objectMapper.writeValueAsString(Result.error("用户已存在", 500));
        }
        user.setPassword(DigestUtils.md5DigestAsHex(user.getPassword().getBytes()));
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
            return objectMapper.writeValueAsString(Result.error("服务器内部错误", 500));
        }
        userFolder = folderService.queryFolder(userFolder.getPath(), userFolder.getUsername());
        user.setUserfid(userFolder.getFid());
        userService.updateUserFid(user);
        return objectMapper.writeValueAsString(Result.success("RegisterSuccess"));
    }

    @RequestMapping("/setMailAddr")
    @ResponseBody
    public String setMailAddress(String mailAddr, ModelMap modelMap, Model model) throws Exception
    {
        String username = (String) modelMap.get("username");
        if (username == null) return objectMapper.writeValueAsString("EMPTY USERNAME");
        if (mailAddr == null) return objectMapper.writeValueAsString("EMPTY ADDRESS");
        model.addAttribute("resetCode", mailServiceImpl.getRandomString());
        model.addAttribute("mailAddr", mailAddr);
        mailService.sendResetPw(mailAddr, username + "的请求码(六位字符）:" + modelMap.get("resetCode"));
        return objectMapper.writeValueAsString(Result.success("setSuccessPlzConfirm"));
    }

    @RequestMapping("/confirmAddr")
    @ResponseBody
    public String confirmAddr(ModelMap modelMap, Model model, String mailCode, String newPassword) throws Exception
    {
        Result res;
        String username = (String) modelMap.get("username");
        if (username == null) return objectMapper.writeValueAsString("EMPTY USERNAME");
        String trueCode = (String) modelMap.get("resetCode");
        if (trueCode.equals(mailCode))
        {
            if (!newPassword.equals("0"))
                userService.editPassword(username, DigestUtils.md5DigestAsHex(newPassword.getBytes()));
            else
                userService.setMailAddr(username, (String) modelMap.get("mailAddr"));
            res = Result.success("confirmSuccess");
            model.addAttribute("resetCode", "");
        } else
        {
            res = Result.error("Incorrect code", 500);
        }
        return objectMapper.writeValueAsString(res);
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
    @ResponseBody
    public String isLogin(ModelMap modelMap) throws Exception
    {
        System.out.println((String) modelMap.get("username"));
        return objectMapper.writeValueAsString(Result.msg("querySuccess", (String) modelMap.get("username"), modelMap.get("username") != null));
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
