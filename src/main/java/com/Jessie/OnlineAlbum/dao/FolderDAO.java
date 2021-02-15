package com.Jessie.OnlineAlbum.dao;

import com.Jessie.OnlineAlbum.entity.Folder;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FolderDAO
{
    @Insert("insert into folder (folderName,path,father,username,size) values(#{folderName},#{path},#{father},#{username},#{size})")
    public void newFolder(Folder folder);

    @Select("select * from folder where fid=#{fid}")
    public Folder getFolder(int fid);

    @Select("select * from folder where father=#{father} and username=#{username}")
    public List<Folder> getUserFolders(@Param("father") int father, @Param("username") String username);

    @Select("select * from folder where path= #{path} and username= #{username}")
    public Folder queryFolder(@Param("path") String path, @Param("username") String username);

    //有多个参数的时候记得要有@Param注释
    @Update("update folder set size=#{size} where fid=#{fid}")
    public void updateSize(@Param("size") long size, @Param("fid") int fid);

    @Delete("delete from folder where fid=#{fid}")
    public void delFolder(int fid);
}
