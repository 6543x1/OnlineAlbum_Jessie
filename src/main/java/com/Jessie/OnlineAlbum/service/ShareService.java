package com.Jessie.OnlineAlbum.service;

import com.Jessie.OnlineAlbum.entity.Share;

import java.util.List;

public interface ShareService
{

    Share getShare(String shareCode);

    List<Share> getUserShare(String shareUser);

    void newShare(Share share);

    void deleteShare(String shareCode);

    void deleteShareByData(int shareData);//因为设置级联弄得两个表，所以就这样吧...

    Share checkShareAvailable(int shareData);
}
