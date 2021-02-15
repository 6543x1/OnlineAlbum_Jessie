package com.Jessie.OnlineAlbum.controller;

import com.Jessie.OnlineAlbum.entity.Folder;
import com.Jessie.OnlineAlbum.entity.Image;
import com.Jessie.OnlineAlbum.service.FolderService;
import com.Jessie.OnlineAlbum.service.ImageService;
import com.sun.org.apache.xpath.internal.operations.Mod;
import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.time.LocalDateTime;
import java.util.UUID;

@Controller
@RequestMapping("/Image")
@SessionAttributes(value = {"username", "userfid"}, types = {String.class, Integer.class})
public class ImageController
{
    @Autowired
    private ImageService imageService;
    @Autowired
    private FolderService folderService;

    @RequestMapping("/fileupload1")
    public String fileupload1(HttpServletRequest request, MultipartFile upload, ModelMap modelmap) throws Exception
    {
        if (modelmap.get("username") == null)
        {
            System.out.println("还没有登录");
            return "error";
        }
        System.out.println("SpringMVC文件上传开始...");
        //String path = request.getSession().getServletContext().getRealPath("/uploads/");
        String path2 = "D:/TomcatImg/";
        //选的是war exploded 那么文件会在工程目录下
        //否则在tomcat目录下
        File file = new File(path2);
        if (!file.exists())
        {
            file.mkdirs();
        }
        String filename = upload.getOriginalFilename();
        String uuid = UUID.randomUUID().toString().replace("-", "");
        filename = uuid + "_" + filename;
        upload.transferTo(new File(path2, filename));
        return "FileSuccess";
    }

    @RequestMapping("/upload")
    @ResponseBody
    //添加ResponseBody返回值会直接接受到字符串
    public String FileUpload(HttpServletRequest request, @RequestParam("album") String album, @RequestParam("upload") MultipartFile upload, ModelMap modelmap) throws Exception
    {
        if (modelmap.get("username") == null)
        {
            System.out.println("还没有登录");
            return "请先登入";
        }
        System.out.println("SpringMVC文件上传开始...");
        //String path = request.getSession().getServletContext().getRealPath("/uploads/");
        String username = (String) modelmap.get("username");
        String path2 = "D:/TomcatImg/" + username + "/" + album + "/";
        //存储结构也要大改，这一部分要加上user文件夹要不然会都重在一起不方便管理
        //算了明天一起改吧
        Folder thisFolder;
        if (folderService.queryFolder(path2, username) == null)
        {
            thisFolder = new Folder();
            thisFolder.setFather((int) modelmap.get("userfid"));
            thisFolder.setFolderName(album);
            thisFolder.setPath(path2);
            thisFolder.setUsername((String) modelmap.get("username"));
            thisFolder.setSize(0);
            folderService.newFolder(thisFolder);
        }
        thisFolder = folderService.queryFolder(path2, username);
        //选的是war exploded 那么文件会在工程目录下
        //否则在tomcat目录下
        System.out.println("文件夹查询成功");
        File file = new File(path2);
        if (!file.exists())
        {
            file.mkdirs();
        }
        try
        {
            String filename = upload.getOriginalFilename();
            Image thisImage = new Image();
            thisImage.setName(filename);
            thisImage.setAlbum(album);
            thisImage.setVisited(1);
            thisImage.setUser((String) modelmap.get("username"));
            thisImage.setPath(path2);
            thisImage.setUploadTime(LocalDateTime.now());
            thisImage.setSize(upload.getSize());
            thisImage.setFid(thisFolder.getFid());
            System.out.println(thisImage.toString());
            imageService.saveImage(thisImage);
            //String uuid = UUID.randomUUID().toString().replace("-", "");
            //可能需要判断一下。。。？
            upload.transferTo(new File(path2, filename));
            System.out.println("文件保存成功，开始向数据库中更新文件夹数据");
            folderService.updateSize(thisFolder.getSize() + thisImage.getSize(), thisFolder.getFid());
        } catch (Exception e)
        {
            e.printStackTrace();
            return "error";
        }
        System.out.println("全部操作已经完成。。。");
        return "FileSuccess";
    }

    @RequestMapping("/uploadById")
    @ResponseBody
    public String UploadById(HttpServletRequest request, @RequestParam("fid") int fid, @RequestParam("upload") MultipartFile upload, ModelMap modelmap) throws Exception
    {
        if (modelmap.get("username") == null)
        {
            System.out.println("还没有登录");
            return "请先登入";
        }
        System.out.println("通过fid文件上传开始...");
        String album = "Default";
        String username = (String) modelmap.get("username");
        Folder thisFolder = folderService.getFolder(fid);
        if (thisFolder == null)
        {
            return "NotExisted";
        }
        if (!thisFolder.getUsername().equals(username))
        {
            return "NotYourFolder";
        }
        String path = thisFolder.getPath();
        //如果文件重名，应该覆盖原文件吧（是否覆盖由前端决定）
        //选的是war exploded 那么文件会在工程目录下
        //否则在tomcat目录下
        File file = new File(path);
        if (!file.exists())
        {
            file.mkdirs();
        }
        try
        {
            String filename = upload.getOriginalFilename();
            Image thisImage = new Image();
            thisImage.setName(filename);
            thisImage.setAlbum(album);
            thisImage.setVisited(1);
            thisImage.setUser((String) modelmap.get("username"));
            thisImage.setPath(path);
            thisImage.setUploadTime(LocalDateTime.now());
            thisImage.setSize(upload.getSize());
            thisImage.setFid(thisFolder.getFid());
            System.out.println(thisImage.toString());
            imageService.saveImage(thisImage);
            //String uuid = UUID.randomUUID().toString().replace("-", "");
            //可能需要判断一下。。。？
            upload.transferTo(new File(path, filename));
            System.out.println("文件保存成功，开始向数据库中更新文件夹数据");
            folderService.updateSize(thisFolder.getSize() + thisImage.getSize(), thisFolder.getFid());
        } catch (Exception e)
        {
            e.printStackTrace();
            return "error";
        }
        return "FileSuccess";
    }

    @RequestMapping("/showImage")
    public void showImage(HttpServletResponse resp, int id) throws Exception
    {
        Image thisImage = imageService.readImage(id);
        try
        {
            InputStream is = new FileInputStream(new File(thisImage.getPath() + thisImage.getName()));
            OutputStream os = new BufferedOutputStream(resp.getOutputStream());
            byte[] buffer = new byte[1024];
            int len = is.read(buffer);
            while (len != -1)
            {
                os.write(buffer, 0, len);
                len = is.read(buffer);
                os.flush();
            }
            os.close();
        } catch (FileNotFoundException e)
        {

            e.printStackTrace();

        }
    }

    @RequestMapping("/download")
    @ResponseBody
    public String downloadFile(HttpServletResponse response, int id, ModelMap modelMap)
    {
        String username = (String) modelMap.get("username");
        Image thisImage = imageService.readImage(id);
        if (thisImage == null)
        {
            return "NotExistedImage";
        }
        if (!thisImage.getUsername().equals(username))
        {
            return "NotYourImage";
        }
        try
        {
            //获取页面输出流
            ServletOutputStream outputStream = response.getOutputStream();
            //读取文件
            byte[] bytes = FileUtils.readFileToByteArray(new File(thisImage.getPath() + thisImage.getName()));
            //向输出流写文件
            //写之前设置响应流以附件的形式打开返回值,这样可以保证前边打开文件出错时异常可以返回给前台
            response.setHeader("Content-Disposition", "attachment;filename=" + thisImage.getName());
            outputStream.write(bytes);
            outputStream.flush();
            outputStream.close();

        } catch (IOException e)
        {
            e.printStackTrace();
            return "error";
        }
        return "success";
    }
}
