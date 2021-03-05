package com.Jessie.OnlineAlbum.entity;

import java.io.Serializable;
import java.time.LocalDateTime;

public class Image implements Serializable
{
    private int imageid;//图片的唯一id，具有唯一性
    private String name; // 图片名称
    private String username; // 创建人
    private int visited; // 访问级别，还没想好，自己可见级别是1
    private String album; // 所属的相册，就是所属文件夹名称
    private String path;// 图片文件的具体路径（指服务器路径）
    private LocalDateTime uploadTime;//图片上传的时间
    private long size;//图片大小
    private int fid;//所属于的文件夹的id

    public int getFid()
    {
        return fid;
    }

    public void setFid(int fid)
    {
        this.fid = fid;
    }

    @Override
    public String toString()
    {
        return "Image{" +
                "name='" + name + '\'' +
                ", username='" + username + '\'' +
                ", visited=" + visited +
                ", album='" + album + '\'' +
                ", path='" + path + '\'' +
                ", UploadTime=" + uploadTime +
                ", size=" + size +
                '}';
    }

    public int getImageid()
    {
        return imageid;
    }

    public void setImageid(int imageid)
    {
        this.imageid = imageid;
    }

    public int getVisited()
    {
        return visited;
    }

    public long getSize()
    {
        return size;
    }

    public void setSize(long size)
    {
        this.size = size;
    }

    public String getUsername()
    {
        return username;
    }

    public void setUsername(String username)
    {
        this.username = username;
    }

    public LocalDateTime getUploadTime()
    {
        return uploadTime;
    }

    public void setUploadTime(LocalDateTime uploadTime)
    {
        this.uploadTime = uploadTime;
    }

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public String getUser()
    {
        return username;
    }

    public void setUser(String user)
    {
        this.username = user;
    }


    public void setVisited(int visited)
    {
        this.visited = visited;
    }

    public String getAlbum()
    {
        return album;
    }

    public void setAlbum(String album)
    {
        this.album = album;
    }

    public String getPath()
    {
        return path;
    }

    public void setPath(String path)
    {
        this.path = path;
    }
}
