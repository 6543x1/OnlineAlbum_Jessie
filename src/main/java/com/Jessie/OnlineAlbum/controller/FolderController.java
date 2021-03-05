package com.Jessie.OnlineAlbum.controller;

import com.Jessie.OnlineAlbum.entity.Folder;
import com.Jessie.OnlineAlbum.entity.Image;
import com.Jessie.OnlineAlbum.entity.Result;
import com.Jessie.OnlineAlbum.service.FolderService;
import com.Jessie.OnlineAlbum.service.ImageService;
import com.Jessie.OnlineAlbum.service.impl.folderServiceImpl;
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
@SessionAttributes(value = {"username", "folderList", "imageList"}, types = {String.class, List.class, List.class})
public class FolderController
{
    @Autowired
    ObjectMapper objectMapper;
    @Autowired
    private FolderService folderService;
    @Autowired
    private ImageService imageService;

    @RequestMapping("/testQueryFolder")
    public void TestQuery()
    {
        String username = "Jessie";
        String path = "D:/TomcatImg/" + "my" + "/";
        System.out.println("开始查询文件夹。。。。");
        Folder folder = folderService.queryFolder(path, username);
        if (folder != null)
            System.out.println(folder.toString());
        else
        {
            System.out.println("不存在的文件夹");
        }
    }

    @RequestMapping("/getUserFolderInfo")
    public String getUserFolders(int father, ModelMap modelMap, Model model)
    {
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

    @RequestMapping("/getCurrentFolders")
    @ResponseBody
    public String getCurrentFolders(int fid, ModelMap modelMap) throws Exception
    {
        String username = (String) modelMap.get("username");
        List<Folder> folderList = folderService.getUserFolders(fid, username);
        System.out.println("获取文件夹数据中" + username + " " + fid);
        try
        {
            for (Folder folder : folderList)
            {
                System.out.println(folder.toString());
            }

        } catch (NullPointerException e)
        {
            System.out.println("当前目录下没有文件夹！");
            Folder folder = new Folder();
            folder.setFid(-2);
            folder.setFolderName("NULL");
            folderList.add(folder);
        }
        Folder fatherFolder;//返回上个文件夹
        if (fid != 0)
        {//禁止返回根目录上一级，虽然这样也没有权限就是了，但是我根目录路径可没改
            fatherFolder = folderService.getFolder(fid);
            fatherFolder.setFid(-3);
        } else
        {
            fatherFolder = new Folder();
            fatherFolder.setFid(-3);
            fatherFolder.setFather(0);//根目录原地tp
            fatherFolder.setPath("*root*");//隐藏服务端细节（你图片不都给你泄漏完了吗）
            fatherFolder.setFolderName(">////<");
        }
        folderList.add(fatherFolder);
        return objectMapper.writeValueAsString(folderList);
    }

    @RequestMapping("/getCurrentImages")
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

    @RequestMapping("/CreateFolder")
    @ResponseBody
    public String CreateFolder(int father, String foldName, ModelMap modelMap) throws Exception
    {
        String username = (String) modelMap.get("username");
        Folder folder = new Folder();
        folder.setUsername(username);
        folder.setSize(0);
        folder.setFather(father);
        folder.setFolderName(foldName);
        Folder theFather = folderService.getFolder(father);
        if (folderService.queryFolder(theFather.getPath() + foldName + "/", username) != null)
        {
            return objectMapper.writeValueAsString(Result.error("已存在同名文件夹", 403));
        }
        if (father == 0)
            folder.setPath(foldName + "/");//决定使用相对路径
        else
            folder.setPath(theFather.getPath() + foldName + "/");
        File file = new File(folder.getPath());
        if (!file.exists())
        {
            file.mkdirs();
        }
        folderService.newFolder(folder);
        return objectMapper.writeValueAsString(Result.success("创建文件夹成功"));
    }

    @RequestMapping("/deleteFolder")
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
        if (!folderServiceImpl.deleteFolder(folderService.getFolder(fid).getPath()))
        {
            return objectMapper.writeValueAsString(Result.error("DeleteFailed", 500));
        }
        folderService.delFolder(fid);
        //还要把所有子文件夹也干掉，但是有点复杂
        return objectMapper.writeValueAsString(Result.success("deleteSuccess"));
    }
}
