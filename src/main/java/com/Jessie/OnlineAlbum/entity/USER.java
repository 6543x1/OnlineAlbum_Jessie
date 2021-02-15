package com.Jessie.OnlineAlbum.entity;

import java.io.Serializable;

public class USER implements Serializable
{
    String username;
    String password;
    int userfid;

    @Override
    public String toString()
    {
        return "USER{" +
                "username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", userfid=" + userfid +
                '}';
    }

    public int getUserfid()
    {
        return userfid;
    }

    public void setUserfid(int userfid)
    {
        this.userfid = userfid;
    }

    public String getUsername()
    {
        return username;
    }

    public void setUsername(String username)
    {
        this.username = username;
    }

    public String getPassword()
    {
        return password;
    }

    public void setPassword(String password)
    {
        this.password = password;
    }
}
