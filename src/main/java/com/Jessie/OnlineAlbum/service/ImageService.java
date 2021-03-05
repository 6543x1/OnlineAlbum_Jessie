package com.Jessie.OnlineAlbum.service;

import com.Jessie.OnlineAlbum.entity.Image;

import java.util.List;

public interface ImageService
{
    void saveImage(Image image);

    Image getImage(int id);

    List<Image> getUserImages(String username, int fid);

    Image isExisted(String name, int fid, String username);

    int deleteImage(int imageid);

    int renameImage(int imageid, String newName);

    int moveImage(int imageid, String newPath, String newAlbum, int fid);
}
