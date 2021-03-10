package com.Jessie.OnlineAlbum.entity;

import java.io.Serializable;

public class Share implements Serializable
{
    int shareType;
    String shareUser;
    String shareCode;
    int shareData;

    @Override
    public String toString()
    {
        return "Share{" +
                "shareType=" + shareType +
                ", shareUser='" + shareUser + '\'' +
                ", shareCode='" + shareCode + '\'' +
                ", shareData=" + shareData +
                '}';
    }

    public int getShareData()
    {
        return shareData;
    }

    public void setShareData(int shareData)
    {
        this.shareData = shareData;
    }

    public int getShareType()
    {
        return shareType;
    }

    public void setShareType(int shareType)
    {
        this.shareType = shareType;
    }

    public String getShareUser()
    {
        return shareUser;
    }

    public void setShareUser(String shareUser)
    {
        this.shareUser = shareUser;
    }

    public String getShareCode()
    {
        return shareCode;
    }

    public void setShareCode(String shareCode)
    {
        this.shareCode = shareCode;
    }


}
