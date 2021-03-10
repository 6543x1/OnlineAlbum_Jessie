package com.Jessie.OnlineAlbum.entity;

import java.io.Serializable;

public class Share implements Serializable
{
    int shareType;
    String shareUser;
    String shareCode;
    int imageid;
    int fid;

    @Override
    public String toString()
    {
        return "Share{" +
                "shareType=" + shareType +
                ", shareUser='" + shareUser + '\'' +
                ", shareCode='" + shareCode + '\'' +
                ", imageid=" + imageid +
                ", fid=" + fid +
                '}';
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

    public int getImageid()
    {
        return imageid;
    }

    public void setImageid(int imageid)
    {
        this.imageid = imageid;
    }

    public int getFid()
    {
        return fid;
    }

    public void setFid(int fid)
    {
        this.fid = fid;
    }
}
