package com.Jessie.OnlineAlbum.service;

import com.Jessie.OnlineAlbum.entity.Folder;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

public interface FolderService
{

    public void newFolder(Folder folder);

    public Folder getFolder(int fid);

    public List<Folder> getUserFolders(int father, String username);

    public Folder queryFolder(String path, String username);

    public void updateSize(long size, int fid);

    public void delFolder(int fid);
}
