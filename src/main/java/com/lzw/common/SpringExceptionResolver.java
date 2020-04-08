package com.lzw.common;

import com.lzw.exception.ParamException;
import com.lzw.exception.PermissionException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;

import javax.naming.NoPermissionException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Slf4j
public class SpringExceptionResolver implements HandlerExceptionResolver {
    @Override
    public ModelAndView resolveException(HttpServletRequest request, HttpServletResponse response, Object o, Exception e) {
        String url =request.getRequestURI().toString();
        ModelAndView mv;
        String defaultmsg="System error";

        // 这里要求项目中所有请求json数据，都是用.json结尾
        if(url.endsWith(".json")){
            if (e instanceof PermissionException || e instanceof ParamException){
                JsonData result = JsonData.fail(e.getMessage());
                mv=new ModelAndView("jsonView",result.toMap());
            } else {
                log.error("unkonwn json exception , url :"+ url,e);
                JsonData result = JsonData.fail(defaultmsg);
                mv=new ModelAndView("jsonView",result.toMap());
            }
        } else if (url.endsWith(".page")){ // 这里要求项目中所有请求page数据，都是用.page结尾
            log.error("unkonwn page exception , url :"+ url,e);
            JsonData result = JsonData.fail(defaultmsg);
            mv=new ModelAndView("exception",result.toMap());
        }else {
            log.error("unkonwn  exception , url :"+ url,e);
            JsonData result = JsonData.fail(defaultmsg);
            mv=new ModelAndView("jsonView",result.toMap());
        }

        return mv;
    }
}
