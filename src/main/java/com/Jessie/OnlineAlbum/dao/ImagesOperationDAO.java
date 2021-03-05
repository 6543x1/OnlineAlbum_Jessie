package com.Jessie.OnlineAlbum.dao;


import com.Jessie.OnlineAlbum.entity.Image;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ImagesOperationDAO
{
    @Insert("insert into image (name,username,album,path,visited,uploadTime,size,fid) values(#{name},#{username},#{album},#{path},#{visited},#{uploadTime},#{size},#{fid})")
    public void saveImage(Image image);

    @Select("select * from image where imageid=#{id}")
    public Image readImage(int id);

    @Select("select * from image where username=#{username} and fid=#{fid}")
    public List<Image> getUserImages(@Param("username") String username, @Param("fid") int fid);

    @Select(("select * from image where name=#{name} and fid=#{fid} and username=#{username}"))
    public Image isExisted(@Param("name") String name, @Param("fid") int fid, @Param("username") String username);

    @Delete("delete from image where imageid=#{imageid}")
    public int deleteImage(int imageid);

    @Update("update image set name=#{newName} where imageid=#{imageid}")
    public int renameImage(@Param("imageid") int imageid, @Param("newName") String newName);

    @Update("update image set path=#{newPath},album=#{newAlbum},fid=#{fid}} where imageid=#{imageid}")
    public int moveImage(@Param("imageid") int imageid, @Param("newPath") String newPath, @Param("newAlbum") String newAlbum, @Param("fid") int fid);
}
