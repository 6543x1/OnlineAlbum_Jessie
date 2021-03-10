package com.Jessie.OnlineAlbum.controller;

import com.Jessie.OnlineAlbum.entity.Folder;
import com.Jessie.OnlineAlbum.entity.Image;
import com.Jessie.OnlineAlbum.entity.Result;
import com.Jessie.OnlineAlbum.entity.Share;
import com.Jessie.OnlineAlbum.service.FolderService;
import com.Jessie.OnlineAlbum.service.ImageService;
import com.Jessie.OnlineAlbum.service.ShareService;
import com.Jessie.OnlineAlbum.service.impl.folderServiceImpl;
import com.Jessie.OnlineAlbum.service.impl.mailServiceImpl;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttributes;

import java.io.File;
import java.util.List;

@Controller
@RequestMapping("/folder")
@SessionAttributes(value = {"username", "folderList", "imageList", "userPath"}, types = {String.class, List.class, List.class, String.class})
public class FolderController
{
    @Autowired
    ObjectMapper objectMapper;
    @Autowired
    private FolderService folderService;
    @Autowired
    private ImageService imageService;
    @Autowired
    private ShareService shareService;

    @RequestMapping(value = "/getUserFolderInfo", produces = "text/html;charset=UTF-8")
    public String getUserFolders(int father, ModelMap modelMap, Model model)
    {
        //这个是我自己弄的网页版本目录浏览，仅用于调试使用
        String username = (String) modelMap.get("username");
        List<Folder> folderList = folderService.getUserFolders(father, username);
        List<Image> imageList = imageService.getUserImages(username, father);//草发现两个参数刚好反着来了。。。。
        System.out.println("获取文件夹数据中" + username + " " + father);
        try
        {
            for (Folder folder : folderList)
            {
                System.out.println(folder.toString());
            }
            model.addAttribute("folderList", folderList);
        } catch (NullPointerException e)
        {
            System.out.println("当前目录下没有文件夹！");
            model.addAttribute("NoFolder", "true");
        }
        System.out.println("获取当前图片数据中" + username + " " + father);
        try
        {
            for (Image image : imageList)
            {
                System.out.println(image.toString());
            }
            model.addAttribute("imageList", imageList);
        } catch (NullPointerException e)
        {
            System.out.println("当前目录下没有图片！");
            model.addAttribute("NoImage", "true");
        }
        return "DownloadPages";
    }

    @RequestMapping(value = "/getCurrentFolders", produces = "text/html;charset=UTF-8")
    @ResponseBody
    public String getCurrentFolders(int fid, ModelMap modelMap) throws Exception
    {
        String username = (String) modelMap.get("username");
        List<Folder> folderList = folderService.getUserFolders(fid, username);
        System.out.println("获取文件夹数据中" + username + " " + fid);
        Folder fatherFolder;//返回上个文件夹
        if (fid != 0)
        {//禁止返回根目录上一级，虽然这样也没有权限就是了，但是我根目录路径可没改
            fatherFolder = folderService.getFolder(fid);
            fatherFolder.setFolderName("返回上一级");
            fatherFolder.setFid(fatherFolder.getFather());
        } else
        {
            fatherFolder = new Folder();
            fatherFolder.setFid(0);
            fatherFolder.setFather(0);//根目录原地tp
            fatherFolder.setPath("*root*");//隐藏服务端细节（你图片不都给你泄漏完了吗）
            fatherFolder.setFolderName("返回上一级");
        }
        folderList.add(0, fatherFolder);

        return objectMapper.writeValueAsString(folderList);
    }

    @RequestMapping(value = "/getCurrentImages", produces = "text/html;charset=UTF-8")
    @ResponseBody
    public String getCurrentImages(int fid, ModelMap modelMap) throws Exception
    {
        String username = (String) modelMap.get("username");
        List<Image> imageList = imageService.getUserImages(username, fid);
        System.out.println("获取图片数据中" + username + " " + fid);
        try
        {
            for (Image image : imageList)
            {
                System.out.println(image.toString());
            }

        } catch (NullPointerException e)
        {
            System.out.println("当前目录下没有图片！");
            Image image = new Image();
            image.setFid(-2);
            image.setName("NULL");
        }
        return objectMapper.writeValueAsString(imageList);
    }

    @RequestMapping(value = "/CreateFolder", produces = "text/html;charset=UTF-8")
    @ResponseBody
    public String CreateFolder(int father, String folderName, ModelMap modelMap) throws Exception
    {
        System.out.println(father + " foldName=" + folderName);
        String username = (String) modelMap.get("username");
        Folder folder = new Folder();
        folder.setUsername(username);
        folder.setSize(0);
        folder.setFather(father);
        folder.setFolderName(folderName);
        Folder theFather = folderService.getFolder(father);
        if (folderService.queryFolder(theFather.getPath() + folderName + "/", username) != null)
        {
            return objectMapper.writeValueAsString(Result.error("已存在同名文件夹", 403));
        }
        if (father == 0)
            folder.setPath(folderName + "/");//决定使用相对路径
        else
            folder.setPath(theFather.getPath() + folderName + "/");
        File file = new File(modelMap.get("userPath") + folder.getPath());
        if (!file.exists())
        {
            file.mkdirs();
        }
        System.out.println(folder.toString());
        folderService.newFolder(folder);
        return objectMapper.writeValueAsString(Result.success("创建文件夹成功"));
    }

    @RequestMapping(value = "/deleteFolder", produces = "text/html;charset=UTF-8")
    @ResponseBody
    public String deleteFolder(int fid, ModelMap modelMap) throws Exception
    {
        if (modelMap.get("username") == null)
        {
            return objectMapper.writeValueAsString(Result.error("NotLogin", 401));
        }
        if (!((String) modelMap.get("username")).equals(folderService.getFolder(fid).getUsername()))
        {
            return objectMapper.writeValueAsString(Result.error("NotAllowed", 403));
        }
        if (!folderServiceImpl.deleteFolder(modelMap.get("userPath") + folderService.getFolder(fid).getPath()))
        {
            return objectMapper.writeValueAsString(Result.error("DeleteFailed", 500));
        }
        Folder folder = folderService.getFolder(fid);
        folderService.delFolder(fid);
        shareService.deleteShareByData(fid);
        //还要把所有子文件夹也干掉，但是有点复杂
        return objectMapper.writeValueAsString(Result.success("deleteSuccess"));
    }

    @RequestMapping(value = "/shareFolder", produces = "text/html;charset=UTF-8")
    @ResponseBody
    public String shareFolder(int fid, ModelMap modelMap) throws Exception
    {
        if (modelMap.get("username") == null)
        {
            return objectMapper.writeValueAsString(Result.error("NotLogin", 401));
        }
        Folder folder = folderService.getFolder(fid);
        if (folder == null) return objectMapper.writeValueAsString(Result.error("NotExisted", 404));
        if (!((String) modelMap.get("username")).equals(folder.getUsername()))
        {
            return objectMapper.writeValueAsString(Result.error("NotAllowed", 403));
        }
        imageService.shareImages(2, fid);
        Share share = new Share();
        share.setShareType(0);
        share.setShareData(fid);
        share.setShareUser((String) modelMap.get("username"));
        share.setShareCode(mailServiceImpl.getRandomString());
        shareService.newShare(share);
        return objectMapper.writeValueAsString(Result.success("shareFolderSuccess", share.getShareCode()));
    }
}
