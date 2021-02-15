package com.Jessie.OnlineAlbum.dao;


import com.Jessie.OnlineAlbum.entity.Image;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ImagesOperationDAO
{
    @Insert("insert into image (name,username,album,path,visited,UploadTime,size,fid) values(#{name},#{username},#{album},#{path},#{visited},#{UploadTime},#{size},#{fid})")
    public void saveImage(Image image);

    @Select("select * from image where imageid=#{id}")
    public Image readImage(int id);

    @Select("select * from image where username=#{username} and fid=#{fid}")
    public List<Image> getUserImages(@Param("username") String username, @Param("fid") int fid);
}
