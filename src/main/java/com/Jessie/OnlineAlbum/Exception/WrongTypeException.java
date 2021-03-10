package com.Jessie.OnlineAlbum.Exception;

public class WrongTypeException extends RuntimeException
{
    public WrongTypeException()
    {
        System.out.println("有用户试图上传其他类型的文件");
    }
}
