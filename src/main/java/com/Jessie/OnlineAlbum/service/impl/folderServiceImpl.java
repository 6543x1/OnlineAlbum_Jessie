package com.Jessie.OnlineAlbum.service.impl;

import com.Jessie.OnlineAlbum.dao.FolderDAO;
import com.Jessie.OnlineAlbum.entity.Folder;
import com.Jessie.OnlineAlbum.service.FolderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("folderService")
public class folderServiceImpl implements FolderService
{
    @Autowired
    private FolderDAO folderDAO;

    @Override
    public void newFolder(Folder folder)
    {
        folderDAO.newFolder(folder);
    }

    @Override
    public Folder getFolder(int fid)
    {
        return folderDAO.getFolder(fid);
    }

    @Override
    public List<Folder> getUserFolders(int father, String username)
    {
        return folderDAO.getUserFolders(father, username);
    }

    @Override
    public Folder queryFolder(String path, String username)
    {
        return folderDAO.queryFolder(path, username);
    }

    @Override
    public void updateSize(long size, int fid)
    {
        folderDAO.updateSize(size, fid);
    }

    @Override
    public void delFolder(int fid)
    {
        folderDAO.delFolder(fid);
    }
}
