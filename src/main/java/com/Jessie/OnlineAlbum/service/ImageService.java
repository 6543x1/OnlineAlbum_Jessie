package com.Jessie.OnlineAlbum.service;

import com.Jessie.OnlineAlbum.entity.Image;

import java.util.List;

public interface ImageService
{
    void saveImage(Image image);

    Image readImage(int id);

    List<Image> getUserImages(String username, int fid);
}
