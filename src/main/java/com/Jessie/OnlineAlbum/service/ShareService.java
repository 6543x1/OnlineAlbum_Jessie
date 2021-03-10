package com.Jessie.OnlineAlbum.service;

import com.Jessie.OnlineAlbum.entity.Share;

import java.util.List;

public interface ShareService
{

    Share getShare(String shareCode);

    List<Share> getUserShare(String shareUser);

    void newShare(Share share);

    void deleteShare(String shareCode);

    Share checkShareExisted(int shareImageID);
}
