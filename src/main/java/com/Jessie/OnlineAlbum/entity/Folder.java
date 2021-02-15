package com.Jessie.OnlineAlbum.entity;

import java.io.Serializable;

public class Folder implements Serializable
{
    private int fid;
    private String folderName;
    private String path;
    private int father;
    private String username;
    private int size;

    @Override
    public String toString()
    {
        return "Folder{" +
                "fid=" + fid +
                ", FoldName='" + folderName + '\'' +
                ", path='" + path + '\'' +
                ", father=" + father +
                ", username='" + username + '\'' +
                ", size=" + size +
                '}';
    }

    public int getFid()
    {
        return fid;
    }

    public void setFid(int fid)
    {
        this.fid = fid;
    }

    public String getFolderName()
    {
        return folderName;
    }

    public void setFolderName(String folderName)
    {
        this.folderName = folderName;
    }

    public String getPath()
    {
        return path;
    }

    public void setPath(String path)
    {
        this.path = path;
    }

    public int getFather()
    {
        return father;
    }

    public void setFather(int father)
    {
        this.father = father;
    }

    public String getUsername()
    {
        return username;
    }

    public void setUsername(String username)
    {
        this.username = username;
    }

    public int getSize()
    {
        return size;
    }

    public void setSize(int size)
    {
        this.size = size;
    }


}
