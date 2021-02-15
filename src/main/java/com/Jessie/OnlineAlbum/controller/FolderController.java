package com.Jessie.OnlineAlbum.controller;

import com.Jessie.OnlineAlbum.dao.FolderDAO;
import com.Jessie.OnlineAlbum.entity.Folder;
import com.Jessie.OnlineAlbum.entity.Image;
import com.Jessie.OnlineAlbum.service.FolderService;
import com.Jessie.OnlineAlbum.service.ImageService;
import org.junit.Test;
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
@RequestMapping("/Image")
@SessionAttributes(value = {"username", "folderList"}, types = {String.class, List.class})
public class FolderController
{
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

    @RequestMapping("/getUserFolders")
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

    @RequestMapping("/CreateFolder")
    @ResponseBody
    public String CreateFolder(int father, String foldName, ModelMap modelMap)
    {
        String username = (String) modelMap.get("username");
        Folder folder = new Folder();
        folder.setUsername(username);
        folder.setSize(0);
        folder.setFather(father);
        folder.setFolderName(foldName);
        Folder theFather = folderService.getFolder(father);
        folder.setPath(theFather.getPath() + foldName + "/");
        File file = new File(folder.getPath());
        if (!file.exists())
        {
            file.mkdirs();
        }
        folderService.newFolder(folder);
        return "success";
    }


}
