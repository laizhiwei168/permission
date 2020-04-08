package com.lzw.filter;

import com.lzw.common.RequestHolder;
import com.lzw.model.SysUser;
import com.sun.net.httpserver.HttpExchange;
import lombok.extern.slf4j.Slf4j;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.logging.LogRecord;

@Slf4j
public class LoginFilter implements Filter {


    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest req =(HttpServletRequest) servletRequest;
        HttpServletResponse resp = (HttpServletResponse) servletResponse;
        //String servletPath = req.getServletPath();

        // 拦截接口请求，把session中的user数据获取下来
        SysUser sysUser = (SysUser) req.getSession().getAttribute("user");
        if(sysUser == null){
            String path ="/signin.jsp";
            resp.sendRedirect(path);
            return;
        }
        // 如果用户存在，就存储用户,并开放连接
        RequestHolder.add(sysUser);
        RequestHolder.add(req);
        filterChain.doFilter(servletRequest,servletResponse);
        return;
    }

    @Override
    public void destroy() {

    }
}
