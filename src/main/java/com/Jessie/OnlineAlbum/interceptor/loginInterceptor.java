package com.Jessie.OnlineAlbum.interceptor;

import com.Jessie.OnlineAlbum.entity.Result;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.PrintWriter;

/**
 * 自定义拦截器，可以用于拦截未登录情况下的操作
 */
public class loginInterceptor implements HandlerInterceptor
{
    @Autowired
    ObjectMapper objectMapper;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception
    {
        //将会在controller执行前拦截，true则放行
        System.out.println("拦截器1已经被执行。。。");
        HttpSession httpSession = request.getSession();
        String username = (String) httpSession.getAttribute("username");

        if (username == null)
        {
            //重置response
            response.reset();
            //设置编码格式
            response.setCharacterEncoding("UTF-8");
            response.setContentType("application/json;charset=UTF-8");
            PrintWriter pw = response.getWriter();
            pw.write(objectMapper.writeValueAsString(Result.error("NotLogin,Interceptor", 401)));
            System.out.println("拦截了一个未登录操作");
            //如果此处true，后面会提示该响应已经被执行了
            return false;
        }
        //request.getRequestDispatcher("/WEB-INF/pages/error.jsp").forward(request,response);
        return true;
    }
    //还有postHandle 会在controller执行完毕后再执行，也就是意味着controller中跳转的操作不一定生效
    //比如return了success但是不会跳到success
    //最后还有afterCompletion 会最后执行，可以用来关闭一些资源
}
