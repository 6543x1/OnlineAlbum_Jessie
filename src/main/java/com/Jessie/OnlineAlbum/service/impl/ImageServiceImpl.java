package com.Jessie.OnlineAlbum.service.impl;

import com.Jessie.OnlineAlbum.dao.ImagesOperationDAO;
import com.Jessie.OnlineAlbum.entity.Image;
import com.Jessie.OnlineAlbum.service.ImageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
    public Image readImage(int id)
    {
        return ImageDAO.readImage(id);
    }

    @Override
    public List<Image> getUserImages(String username, int fid)
    {
        return ImageDAO.getUserImages(username, fid);
    }
}
