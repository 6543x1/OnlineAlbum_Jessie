package com.Jessie.OnlineAlbum.service;

import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 自定义拦截器测试，可以用于拦截未登录情况下的操作
 */
public class MyInterceptor1 implements HandlerInterceptor
{
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception
    {
        //将会在controller执行前拦截，true则放行
        System.out.println("拦截器1已经被执行。。。");
        //request.getRequestDispatcher("/WEB-INF/pages/error.jsp").forward(request,response);
        return true;
    }
    //还有postHandle 会在controller执行完毕后再执行，也就是意味着controller中跳转的操作不一定生效
    //比如return了success但是不会跳到success
    //最后还有afterCompletion 会最后执行，可以用来关闭一些资源
}
