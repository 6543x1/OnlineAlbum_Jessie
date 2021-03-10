package com.Jessie.OnlineAlbum.service.impl;

import com.Jessie.OnlineAlbum.dao.ShareDAO;
import com.Jessie.OnlineAlbum.entity.Share;
import com.Jessie.OnlineAlbum.service.ShareService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("shareService")
public class shareServiceImpl implements ShareService
{
    @Autowired
    private ShareDAO shareDAO;

    @Override
    public Share getShare(String shareCode)
    {
        return shareDAO.getShare(shareCode);
    }

    @Override
    public List<Share> getUserShare(String shareUser)
    {
        return shareDAO.getUserShare(shareUser);
    }

    @Override
    public void newShare(Share share)
    {
        shareDAO.newShare(share);
    }

    @Override
    public void deleteShare(String shareCode)
    {
        shareDAO.deleteShare(shareCode);
    }

    @Override
    public void deleteShareByData(int shareData)
    {
        shareDAO.deleteShareByData(shareData);
    }

    @Override
    public Share checkShareAvailable(int shareData)
    {
        return shareDAO.checkShareAvailable(shareData);
    }
}
