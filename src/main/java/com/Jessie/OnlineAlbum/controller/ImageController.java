package com.Jessie.OnlineAlbum.controller;

import com.Jessie.OnlineAlbum.entity.Folder;
import com.Jessie.OnlineAlbum.entity.Image;
import com.Jessie.OnlineAlbum.entity.Result;
import com.Jessie.OnlineAlbum.service.FolderService;
import com.Jessie.OnlineAlbum.service.ImageService;
import com.Jessie.OnlineAlbum.service.impl.ImageServiceImpl;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
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
@RequestMapping("/image")
@SessionAttributes(value = {"username", "userfid", "userPath"}, types = {String.class, Integer.class, String.class})
public class ImageController
{
    @Autowired
    ObjectMapper objectMapper = new ObjectMapper();
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

    @RequestMapping("/uploadByAlbum")
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

    @RequestMapping("/upload")
    @ResponseBody
    public String UploadById(HttpServletRequest request, @RequestParam("fid") int fid, @RequestParam("upload") MultipartFile upload, ModelMap modelmap) throws Exception
    {
        if (modelmap.get("username") == null)
        {
            System.out.println("还没有登录");
            return "请先登入";
        }
        System.out.println("通过fid文件上传开始...");
        String album = "DefaultName";
        String username = (String) modelmap.get("username");
        Folder thisFolder = folderService.getFolder(fid);
        if (thisFolder == null)
        {
            return objectMapper.writeValueAsString(Result.error("NotExisted", 404));
        }
        if (!thisFolder.getUsername().equals(username))
        {
            return objectMapper.writeValueAsString(Result.error("NotAllowed", 403));
        }
        String path = (String) modelmap.get("userPath") + thisFolder.getPath();
        System.out.println(path);
        //如果文件重名，应该覆盖原文件吧（是否覆盖由前端决定）
        //选的是war exploded 那么文件会在工程目录下
        //否则在tomcat目录下
        File file = new File(path);
        if (!file.exists())
        {
            return objectMapper.writeValueAsString(Result.error("服务器上无此文件夹", 404));
        }
        try
        {
            String filename = upload.getOriginalFilename();
            if (filename == null)
            {
                return objectMapper.writeValueAsString(Result.error("找不到原始文件名", 404));
            }
            Image thisImage = new Image();
            thisImage.setName(filename);
            thisImage.setAlbum(thisFolder.getFolderName());
            thisImage.setVisited(1);//默认访问权限1，仅用户本人和管理员可见
            thisImage.setUser((String) modelmap.get("username"));
            thisImage.setPath(thisFolder.getPath());
            thisImage.setUploadTime(LocalDateTime.now());
            thisImage.setSize(upload.getSize());
            thisImage.setFid(thisFolder.getFid());
            System.out.println(thisImage.toString());
            if (imageService.isExisted(thisImage.getName(), thisImage.getFid(), thisImage.getUsername()) != null)
            {
                thisImage.setName(LocalDateTime.now().toString().replace(".", "").replace(":", "_") + "_" + filename);//重名文件后加上传时间，要不数据库会有重复记录
                System.out.println(thisImage.getName());
            }
            imageService.saveImage(thisImage);
            upload.transferTo(new File(path, thisImage.getName()));
            System.out.println("文件保存成功，开始向数据库中更新文件夹数据");
            folderService.updateSize(thisFolder.getSize() + thisImage.getSize(), thisFolder.getFid());
        } catch (Exception e)
        {
            e.printStackTrace();
            return objectMapper.writeValueAsString(Result.error("error"));
        }
        return objectMapper.writeValueAsString(Result.success("uploadSuccess"));
    }

    @RequestMapping("/showImage")
    public void showImage(HttpServletResponse resp, int id) throws Exception
    {
        Image thisImage = imageService.getImage(id);
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
    public String downloadFile(HttpServletResponse response, int imageid, ModelMap modelMap) throws Exception
    {
        String username = (String) modelMap.get("username");
        Image thisImage = imageService.getImage(imageid);
        if (thisImage == null)
        {
            return objectMapper.writeValueAsString(Result.error("文件不存在", 404));
        }
        if (!thisImage.getUsername().equals(username) || thisImage.getVisited() != 1)
        {
            return objectMapper.writeValueAsString(Result.error("你没有权限", 403));
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
            return objectMapper.writeValueAsString(Result.error("服务器发生错误", 500));
        }
        return objectMapper.writeValueAsString(Result.success("开始下载"));
    }

    @RequestMapping(value = "/deleteImage", produces = "text/html;charset=UTF-8")
    @ResponseBody
    public String deleteImage(int imageid, ModelMap modelMap) throws Exception
    {
        if (modelMap.get("username") == null)
        {
            return objectMapper.writeValueAsString(Result.error("没登录", 401));
        }
        if (imageService.getImage(imageid).getName().equals((String) modelMap.get("username")))
        {
            return objectMapper.writeValueAsString(Result.error("没权限", 403));
        }
        Image thisImage = imageService.getImage(imageid);
        if (thisImage == null)
        {
            return objectMapper.writeValueAsString(Result.error("不存在的文件", 404));
        }
        File file = new File(modelMap.get("userPath") + thisImage.getPath() + thisImage.getName());
        file.delete();
        imageService.deleteImage(imageid);
        //删除其在数据库中记录
        return objectMapper.writeValueAsString(Result.success("删除成功"));
    }

    @RequestMapping(value = "/renameImage", produces = "text/html;charset=UTF-8")
    @ResponseBody
    public String renameImage(int imageid, String newName, ModelMap modelMap) throws Exception
    {
        Image image = imageService.getImage(imageid);
        if (image == null)
        {
            return objectMapper.writeValueAsString(Result.error("不存在的文件", 404));
        } else if (!image.getUsername().equals(modelMap.get("username")))
        {
            return objectMapper.writeValueAsString(Result.error("没有权限", 403));
        }
        imageService.renameImage(imageid, newName);
        ImageServiceImpl.renameImage(image, newName);
        return objectMapper.writeValueAsString(Result.success("重命名成功"));
    }

    @RequestMapping(value = "/copyImage", produces = "text/html;charset=UTF-8")
    @ResponseBody
    public String copyImage(int imageid, int destFid, ModelMap modelMap) throws Exception
    {
        Image thisImage = imageService.getImage(imageid);
        if (thisImage == null)
        {
            return objectMapper.writeValueAsString(Result.error("图片不存在", 404));
        }
        if (!thisImage.getUsername().equals((String) modelMap.get("username")))
        {
            return objectMapper.writeValueAsString(Result.error("没有权限！", 403));
        }
        System.out.println(thisImage.toString());
        Folder dest = folderService.getFolder(destFid);//如果一直查询数据库，运行效率是很低下的
        String newPath = (String) modelMap.get("userPath") + dest.getPath() + thisImage.getName();
        ImageServiceImpl.copyImage(modelMap.get("userPath") + thisImage.getPath() + thisImage.getName(), newPath);
        Image newImage = thisImage;//草这是引用的传递，我就说为啥两个路径一样
        //写个clone好麻烦不写了
        newImage.setUploadTime(LocalDateTime.now());
        newImage.setFid(destFid);
        newImage.setPath(dest.getPath());
        newImage.setAlbum(dest.getFolderName());
        imageService.saveImage(newImage);
        return objectMapper.writeValueAsString(Result.success("复制成功"));
    }

    @RequestMapping(value = "/moveImage", produces = "text/html;charset=UTF-8")
    @ResponseBody
    public String moveImage(int imageid, int destFid, ModelMap modelMap) throws Exception
    {
        Image thisImage = imageService.getImage(imageid);
        System.out.println(thisImage.toString());
        if (thisImage == null)
        {
            return objectMapper.writeValueAsString(Result.error("图片不存在", 404));
        }
        if (!thisImage.getUsername().equals((String) modelMap.get("username")))
        {
            return objectMapper.writeValueAsString(Result.error("没有权限！", 403));
        }
        Folder dest = folderService.getFolder(destFid);
        String newPath = modelMap.get("userPath") + dest.getPath() + thisImage.getName();
        ImageServiceImpl.moveImage(modelMap.get("userPath") + thisImage.getPath() + thisImage.getName(), newPath);
        imageService.moveImage(imageid, newPath, dest.getFolderName(), dest.getFid());
        return objectMapper.writeValueAsString(Result.success("移动成功"));
    }

}
