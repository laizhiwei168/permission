package com.lzw.common;

import com.lzw.model.SysUser;
import javax.servlet.http.HttpServletRequest;

/**
 * 存储每个进程对象的实体类
 * ThreadLocal对象是存储当前进程
 */
public class RequestHolder {
    private static final ThreadLocal<SysUser> userHolder = new ThreadLocal<SysUser>();

    private static final ThreadLocal<HttpServletRequest> requestHolder = new ThreadLocal<HttpServletRequest>();

    public static void  add(SysUser sysUser){
        userHolder.set(sysUser);
    }

    public static  void  add(HttpServletRequest request){
        requestHolder.set(request);
    }
    public static  SysUser getCurrentUser(){
        return userHolder.get();
    }
    public static HttpServletRequest getCurrentRequest(){
        return requestHolder.get();
    }

    public  static  void  remove(){
        userHolder.remove();
        requestHolder.remove();
    }

}
