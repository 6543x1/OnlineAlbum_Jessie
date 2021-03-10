package com.Jessie.OnlineAlbum.service.impl;

import com.Jessie.OnlineAlbum.dao.ImagesOperationDAO;
import com.Jessie.OnlineAlbum.entity.Image;
import com.Jessie.OnlineAlbum.service.ImageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.nio.file.Files;
import java.util.List;


@Service("ImageService")
public class ImageServiceImpl implements ImageService
{
    @Autowired
    private ImagesOperationDAO ImageDAO;
    @Override
    public void saveImage(Image image)
    {
        System.out.println("saveImage...");
        ImageDAO.saveImage(image);
    }

    @Override
    public Image getImage(int id)
    {
        return ImageDAO.readImage(id);
    }

    @Override
    public List<Image> getUserImages(String username, int fid)
    {
        return ImageDAO.getUserImages(username, fid);
    }

    @Override
    public Image isExisted(String name, int fid, String username)
    {
        return ImageDAO.isExisted(name, fid, username);
    }

    @Override
    public int deleteImage(int imageid)
    {
        return ImageDAO.deleteImage(imageid);
    }

    @Override
    public int renameImage(int imageid, String newName)
    {
        return ImageDAO.renameImage(imageid, newName);
    }

    @Override
    public int moveImage(int imageid, String newPath, String newAlbum, int fid)
    {
        return ImageDAO.moveImage(imageid, newPath, newAlbum, fid);
    }

    @Override
    public void shareImages(int visited, int fid)
    {
        ImageDAO.shareImages(visited, fid);
    }

    @Override
    public void shareImage(int visited, int imageid)
    {
        ImageDAO.shareImages(visited, imageid);
    }

    public static boolean renameImage(Image image, String newName)
    {
        File file = new File(image.getPath() + image.getName());
        File newFile = new File(image.getPath() + newName);//传参数，记得全名奥
        if (newFile.exists())
        {
            return false;
        } else
        {
            return file.renameTo(newFile);
        }

    }

    public static boolean copyImage(String src, String dest)
    {
        //注意传的时候后面要加文件名不然拒绝访问
        System.out.println("src:" + src + " " + "dest" + dest);
        boolean result = true;
        try
        {
            File file1 = new File(src);
            File file2 = new File(dest);
            Files.copy(file1.toPath(), file2.toPath());
        } catch (Exception e)
        {
            e.printStackTrace();
            result = false;
        }
        return result;
    }

    public static boolean moveImage(String src, String dest)
    {
        boolean result = true;
        System.out.println("src:" + src + " " + "dest" + dest);
        try
        {
            File srcPath = new File(src);
            File destPath = new File(dest);
            result = srcPath.renameTo(destPath);
        } catch (Exception e)
        {
            e.printStackTrace();
            result = false;
        }
        return result;
    }
}
