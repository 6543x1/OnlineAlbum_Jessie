package com.Jessie.OnlineAlbum.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectDB
{
    private static final String JDBC_URL = "jdbc:mysql://localhost:3306/covid?useSSL=false&characterEncoding=utf8";
    private static final String JDBC_USER = "qcovid";//不要随便给root权限，小心删库跑路
    private static final String JDBC_PASSWORD = "123456";

    public static Connection getConn() throws SQLException
    {
        return DriverManager.getConnection(JDBC_URL, JDBC_USER, JDBC_PASSWORD);
    }

    public static void closeConn(Connection conn)
    {
        if (null != conn)
        {
            try
            {
                conn.close();
            } catch (SQLException e)
            {
                System.out.println("关闭连接失败！");
                e.printStackTrace();
            }
        }
    }
}
