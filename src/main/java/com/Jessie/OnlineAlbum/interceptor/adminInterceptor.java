package com.Jessie.OnlineAlbum.interceptor;

import com.Jessie.OnlineAlbum.entity.Result;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.PrintWriter;

public class adminInterceptor implements HandlerInterceptor
{
    @Autowired
    ObjectMapper objectMapper;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception
    {
        HttpSession httpSession = request.getSession();
        String username = (String) httpSession.getAttribute("username");

        if (username == null || !username.equals("Jessie"))//暂定的管理员用户
        {
            //重置response
            response.reset();
            //设置编码格式
            response.setCharacterEncoding("UTF-8");
            response.setContentType("application/json;charset=UTF-8");
            PrintWriter pw = response.getWriter();
            pw.write(objectMapper.writeValueAsString(Result.error("禁止非管理员账户访问", 403)));
            System.out.println("拦截了一个非管理员的操作");
            return false;
        }
        return true;
    }
}
