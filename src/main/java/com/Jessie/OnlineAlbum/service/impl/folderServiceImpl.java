package com.Jessie.OnlineAlbum.service.impl;

import com.Jessie.OnlineAlbum.dao.FolderDAO;
import com.Jessie.OnlineAlbum.entity.Folder;
import com.Jessie.OnlineAlbum.service.FolderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.List;
import java.util.Objects;

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

    public static boolean deleteFolder(String path) throws Exception
    {
        try
        {

            File file = new File(path);
            // 当且仅当此抽象路径名表示的文件存在且 是一个目录时，返回 true
            if (!file.isDirectory())
            {
                file.delete();
            } else if (file.isDirectory())
            {
                String[] fileList = file.list();
                for (int i = 0; i < Objects.requireNonNull(fileList).length; i++)
                {
                    File delfile = new File(path + fileList[i]);
                    if (!delfile.isDirectory())
                    {
                        delfile.delete();
                        System.out
                                .println(delfile.getAbsolutePath() + "删除文件成功");
                    } else if (delfile.isDirectory())
                    {
                        deleteFolder(path + fileList[i] + "/");
                    }
                }
                System.out.println(file.getAbsolutePath() + "删除成功");
                file.delete();
            }
        } catch (FileNotFoundException e)
        {
            System.out.println("deletefile() Exception:" + e.getMessage());
        }
        return true;
    }
}
